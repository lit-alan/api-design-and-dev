# Lab Exercise :three:
:point_down:

Validation is essential in APIs because it protects the system from invalid, inconsistent, or malicious input. By rejecting bad data early, validation keeps the data clean, avoids errors later in the system, and means the core code doesn’t have to constantly check for invalid input. Just as importantly, well-designed validation forms part of the API contract: it tells clients what inputs are acceptable and how to recover when they get it wrong, leading to more reliable and predictable client–server interactions. For this lab exercise you are required to use the source code for the [Authors REST API](https://github.com/lit-alan/api-design-and-dev/tree/master/Lectures/L2%20SOAP%20and%20REST/REST) from Lecture Two.


## TASK ONE.

 Add the followng Bean Validation to the `Author` entity.

_firstName_

- Must be present and not blank.

- Must be between 1 and 50 characters.

- Must match the provided `HUMAN_NAME_REGEX` (letters only, with optional internal hyphens or apostrophes).

_lastName_

- Must be present and not blank.

- Must be between 1 and 80 characters.

- Must match the provided `HUMAN_NAME_REGEX`.

_yearBorn_

- Must be greater than or equal to 1000.

- Must be less than or equal to 2026 (i.e. not in the future).

- You must then insure that the `POST /api/v2/authors/` and `PUT /api/v2/authors/` endpoints reject invalid payloads that violate the validation constraints with a consistent error response.

To begin you must add the following Maven dependency to the POM.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

Replace the existing `Author` entity with the following:

```java
public class Author  {

    //RegX for the first and last name.
    //letters only at start
    //allows internal segments joined by hyphen/apostrophe
    //no spaces, no leading/trailing punctuation, no consecutive punctuation
    private static final String HUMAN_NAME_REGEX = "^(?=.{1,80}$)[A-Za-z]+(?:[-'][A-Za-z]+)*$";


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long authorID;

    @NotBlank(message = "firstName is required")
    @Size(min = 1, max = 50, message = "firstName must be 1..50 characters")
    @Pattern(regexp = HUMAN_NAME_REGEX, message = "firstName contains invalid characters")
    private String firstName;

    @NotBlank(message = "lastName is required")
    @Size(min = 1, max = 80, message = "lastName must be 1..80 characters")
    @Pattern(regexp = HUMAN_NAME_REGEX, message = "lastName contains invalid characters")
    private String lastName;

    @Min(value = 1000, message = "yearBorn must be >= 1000")
    @Max(value = 2026, message = "yearBorn cannot be in the future") //I'm hard coding the year here - not ideal
    private int yearBorn;
}
```

In `AuthorControllerV2`, add `@Valid` before `@RequestBody Author a` in the method signatures for both the `POST` and `PUT` endpoints. The `@Valid` annotation triggers Bean Validation on the request body before the controller method executes.

  
`public ResponseEntity add(@Valid @RequestBody Author a) {...} `


Then add the following method to your V2 controller. This code will handle Bean Validation failures on `@RequestBody` parameters:

```java
//This method intercepts MethodArgumentNotValidException (thrown before the controller
//method executes), extracts field-level validation errors, and returns a simple
//field → message map with a 400 Bad Request response for invalid client input.
@ResponseStatus(HttpStatus.BAD_REQUEST)
@ExceptionHandler(MethodArgumentNotValidException.class)
public Map<String, String> handleValidationExceptions(
        MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
    });

   return errors;
}

```
### Test the endpoints in Postman

| ![image](https://github.com/user-attachments/assets/403a2ac8-c304-4547-9f7a-ad64d6a71f9d) |
|:--------------------------------------------------------------------------------------:|
| **Make a `POST` request to `http://localhost:8080/api/v2/authors` without submitting a value for the firstName and observe the response** |



| ![image](https://github.com/user-attachments/assets/43612ae1-bcc6-45cd-897b-fba2f47f40a7) |
|:--------------------------------------------------------------------------------------:|
| **Make a `POST` request to `http://localhost:8080/api/v2/authors` without submitting a value for the firstName and supplying a yearBorn in the future and observe the response** |

_Note 1:_ The validation can also be tested using a PUT request.

_Note 2:_ A valid request should result in an Author record being inserted or updated (depending on the request type) in the database.

## TASK TWO

The approach we took in Task One works, but it does not scale as validation rules or API complexity increase (it only handles request-body validation and ignores path and query parameter validation). Another major drawback is that this solution is tied to a single controller, making validation error handling inconsistent across API's that have more than one controller.

The following is a better approach:

To begin, comment out the handler you added to the V2 controller.
  
Secondly, add `@Validated` to the controller (above `@RestController`)  - this enables Bean Validation on controller method parameters (e.g. @PathVariable, @RequestParam).  

Thirdly, add a package called `validation` under `main` and place the following class in it. This class is responsible for handling validation errors and generating appropriate error responses when validation fails.

```java

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationAdvice {

//RFC 7807 (an Internet standard specification) defines a structured way to represent errors in HTTP APIs;
//this URI is the problem type identifier used by that standard to
//classify errors.
//RFC 7807 defines the `type` field as a URI that identifies the category of error.
//This link is a machine-readable identifier and is not required to be a real or
//navigable URL; it acts as a unique name that clients can use to distinguish
//validation errors from other error types.
private static final URI TYPE =
        URI.create("http://localhost:8080/problems/validation-error");


    //@Valid @RequestBody (POST / PUT)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ProblemDetail handleBodyValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pd.setType(TYPE);
        pd.setTitle("Validation failed");
        pd.setDetail("One or more fields are invalid.");
        pd.setInstance(URI.create(request.getRequestURI()));

        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));

        pd.setProperty("errors", errors);
        return pd;
    }
```
RFC 7807 is an Internet standard (published in March 2016) that defines a uniform, structured way to represent errors in HTTP APIs. Before RFC 7807, APIs returned errors in wildly different shapes:
- Plain strings

- Ad-hoc JSON objects

- Framework-specific error dumps

- HTML error pages

This meant clients had no reliable, standard way to understand what kind of error occurred. It’s not a new idea, but it took several years before frameworks like Spring adopted it as a default error representation.

RFC 7807 defines these fields:

| Field      |	Purpose   |
|----------- |----------- |
|type        |	URI identifying the problem category
|title	    |Short, human-readable summary
|status	    |HTTP status code
|detail	    |Human-readable explanation
|instance	  |URI identifying the specific request

_Only type and title are strictly required._

Spring only performs component scanning starting from the package of the main application class and all of its subpackages. If controllers, services, or repositories live outside that package hierarchy, Spring will not detect them unless you explicitly tell it where to look.

In this project, the application class currently includes the following configuration:

```java
@ComponentScan({"com.main.service", "com.main.controllers"})
@EntityScan("com.main.model")
@EnableJpaRepositories("com.main.repository")
```
Remove these three annotations.

:warning: DO NOT REMOVE `@SpringBootApplication` FROM THE APPLICATION CLASS - if you do, the application will not start :warning:
  
The explicit scanning shown above is not strictly necessary. If the application class is placed in the `com.main` package, Spring will automatically discover all components in its subpackages (controllers, service, model, repository, etc.). In that case, these additional scan annotations can safely be removed.

This should leave you with a project that has a structure like this:

| ![image](https://github.com/user-attachments/assets/97c6c234-2266-430e-a513-fdb06e496048) |
|:--------------------------------------------------------------------------------------:|


### Test the endpoints in Postman

| ![image](https://github.com/user-attachments/assets/83bbc41a-11dd-4ca8-aca6-67d1eef1697b) |
|:--------------------------------------------------------------------------------------:|
| **Make a `POST` request to `http://localhost:8080/api/v2/authors` without submitting a value for the firstName and observe the response** |



| ![image](https://github.com/user-attachments/assets/02139d78-21fb-4936-9e90-2ddaae23ea18) |
|:--------------------------------------------------------------------------------------:|
| **Make a `POST` request to `http://localhost:8080/api/v2/authors` without submitting a value for the firstName and supplying a yearBorn in the future and observe the response** |




## TASK THREE.

Observe the endpoint 

```java
 @GetMapping("/authors/bornbetween/{start}/{end}")
    public List<Author> getBornBetween(
            @PathVariable Integer start,
            @PathVariable Integer end) {
        return authorService.findAuthorByYearBornBetweenOrderByFirstName(start, end);
    }
```
This endpoint uses path parameters to specify a start and end year, and returns a list of authors whose year of birth falls within that range.

For example, the request:

```properties
GET /api/v2/authors/bornbetween/1980/2026
```
returns all authors born between 1980 (start) and 2026 (end) in the response body.

However, there is currently no validation on the start and end values. What happens if a client supplies an unrealistic or invalid range? How could we enforce minimum and maximum bounds on these path parameters?

We will add validation to the start and end path parameters to restrict the allowable year range, enforcing a minimum value of 1000 and a maximum value of 2026. Firstly, replace the existing endpoint with the following:

```java
@GetMapping("/authors/bornbetween/{start}/{end}")
public List<Author> getBornBetween(
        @PathVariable @Min(1000) @Max(2026) Integer start,
        @PathVariable @Min(1000) @Max(2026) Integer end) {
    return authorService.findAuthorByYearBornBetweenOrderByFirstName(start, end);
}
```

This alone won't enforce validation and the return of a standardised error message, so add the following method to your `ValidationAdvice` class.

```java
//Handles validation failures on path and query parameters.
// his method is invoked when Bean Validation constraints on method parameters
//(e.g. @Min, @Max on @PathVariable or @RequestParam) fail and a
// ConstraintViolationException is thrown before the controller method executes.
@ExceptionHandler(ConstraintViolationException.class)
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public ProblemDetail handleParamValidation(
      ConstraintViolationException ex,
      HttpServletRequest request) {

  //Create an RFC 7807 ProblemDetail response with status 422 (semantic error)
  ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
  pd.setType(TYPE);                       //identifies this as a validation error
  pd.setTitle("Validation failed");       //human-readable summary
  pd.setDetail("One or more parameters are invalid.");
  pd.setInstance(URI.create(request.getRequestURI())); //the request that caused the error

  //collect parameter-specific validation errors (e.g. start, end)
  Map<String, String> errors = new LinkedHashMap<>();
  ex.getConstraintViolations().forEach(v ->
          errors.put(v.getPropertyPath().toString(), v.getMessage()));

  //attach the parameter errors to the ProblemDetail response
  pd.setProperty("errors", errors);
  return pd;
}
```

_ProblemDetail is Spring Boot’s built-in representation of an API error response, based on RFC 7807_

### Test the endpoint in Postman

| ![image](https://github.com/user-attachments/assets/bd489bcc-e067-4e02-bf9e-14e7a65dd956) |
|:--------------------------------------------------------------------------------------:|


_As this is a GET request you can also test it in a browser._

As previously discussed, this endpoint currently uses path parameters. Rewrite the method so that it uses request parameters (also known as query parameters) instead and ensure that the request parameters are validated in the same way as the original path parameters.

The following request demonstrates the use of query parameters:

```properties
GET /api/v2/authors/bornbetween?start=1940&end=1970
```
This request returns all authors born between 1940 and 1970.

Test the following request which supplies values outside the allowed range and should trigger the validation rules, resulting in an error response:

```properties
GET /api/v2/authors/bornbetween?start=199&end=2027
```

- Query parameters are often preferred when the parameters are optional or configurable. Path parameters identify what resource you are accessing while query parameters describe how you want it filtered.

- Use path parameters when the value is essential to identifying the resource or endpoint.
- Use query parameters when the value is a filter, search criterion, or optional modifier.
`bornbetween` represents a query/filter operation, not a unique resource so query parameters (start, end) is the better choice here.

## TASK FOUR

Right now, both `POST` and `PUT` blindly call `authorRepo.save(a)`. This is a problem because `JpaRepository.save(...)` performs an *upsert* (insert or update depending on the entity ID). The net result is that the API can behave incorrectly for POST/PUT requests. As it stands:

**PUT creates new records** if a client sends a (`PUT`) request containing an authorID that does not exist in the database, `save()` inserts a new record. This is not the behaviour we want: `PUT` should update an existing resource, not create one.

**POST updates existing records** if a client sends a (`POST`) request containing an authorID that already exists, `save()` updates the existing record. This is not the behaviour we want: `POST` should create a new resource and should not allow clients to overwrite existing authors.



The goal here is to refactor the controller/service layer so that the API follows correct CRUD semantics:

`POST /api/v2/authors`
Must create only and **must not update existing authors**. Client-supplied IDs must not be allowed to control updates.

`PUT /api/v2/authors` (or `/api/v2/authors/{id}` if you choose to redesign)
Must update only and **must not create new authors**. If the target authorID does not exist, return 404 Not Found.

For this task implement the following rules.

**POST:**

If the request body contains a non-zero `authorID`, reject the request with a client error response (e.g. 400 or 422). When creating a new author with a `POST` request, the client must not supply an `authorID`, as IDs are generated by the server. Allowing clients to choose IDs can lead to unintended updates or duplicate records.

Otherwise, create a new author and return 201 Created.

**PUT:**

For `PUT` requests, the request body must include a valid, non-zero `authorID`. If the `authorID` is missing or zero, reject the request with a client error response (400 or 422), as the server cannot determine which author to update.

If the supplied `authorID` does not exist in the database, return 404 Not Found.

If the authorID exists, update the author and return 200 OK.

In summary

|Request scenario                           |	Result          |	Effect                                |
| ----------------------------------------- | --------------- | ------------------------------------- |
| `PUT` with `authorID = 0` or missing      | `400` or `422`  | Request rejected; no update performed |
| `PUT` with `authorID` that does not exist | `404 Not Found` | Request rejected; no record created   |
| `PUT` with existing `authorID`            | `200 OK`        | Existing author record updated        |
| `POST` with non-zero `authorID`           | `400` or `422`  | Request rejected; no record created   |
| `POST` with `authorID` provided           | `200 OK`        | New author record inserted            |


_Hint_

You will likely need to separate the service logic into two methods, for example:

`createAuthor(...)` for `POST` semantics

`updateAuthor(...)` for `PUT` semantics

and perform an existence check (e.g. `existsById(...)`) before allowing updates.

If you want the error response to match your the previous `ProblemDetail` responses, add the following to your `ValidationAdvice` class.



```java
@ExceptionHandler(ResponseStatusException.class)
public ResponseEntity<ProblemDetail> handleResponseStatusException(ResponseStatusException ex,
                                                                   HttpServletRequest request) {
    ProblemDetail pd = ProblemDetail.forStatus(ex.getStatusCode());
    pd.setType(URI.create("https:/localhost:8080/problems/request-error"));
    pd.setTitle(ex.getStatusCode().toString());
    pd.setDetail(ex.getReason());
    pd.setInstance(URI.create(request.getRequestURI()));
    return ResponseEntity.status(ex.getStatusCode()).body(pd);
}
```

The application should behave like the following:


| ![image](https://github.com/user-attachments/assets/dfe79c46-3d00-4ef3-8a72-8cff8efe7418) |
|:--------------------------------------------------------------------------------------:|


| ![image](https://github.com/user-attachments/assets/60865d65-9d62-4d1f-8b56-d142d9e495f3) |
|:--------------------------------------------------------------------------------------:|

| ![image](https://github.com/user-attachments/assets/486cb3f2-b678-48c4-99d0-b75ec052fefe) |
|:--------------------------------------------------------------------------------------:|


_At this point, your API should be harder to break than it was at the start._ :sunglasses:
