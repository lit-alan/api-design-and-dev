
# Lab Exercise :one:

Create a Spring Boot project in IntelliJ and add the following dependencies to it (the first one `Spring Web`, can be added when creating the project using the Spring Initializr).

```xml
 <dependencies>
    <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
      </dependency>

      <!-- Spring Boot natively supports JSON using Jackson (jackson-databind), but XML is not enabled by default. With this dependency, Spring Boot can automatically serialize Java objects into XML format -->
      <dependency>
          <groupId>com.fasterxml.jackson.dataformat</groupId>
          <artifactId>jackson-dataformat-xml</artifactId>
      </dependency>
</dependencies>
```
Using the supplied starter code, complete the following tasks, creating a seperate REST Controller for each task.  

Test these tasks using [Postman](https://www.postman.com/) or an equilavent

## Task One: Content Negotiation in a News API
### Scenario
You're working on a news service API where users can fetch articles in JSON, XML, or plain text format, depending on the value set in the `Accept` header in the request.
- Implement an endpoint (`/api/news/{id}`) that:  
  - Returns a news article in JSON if **Accept: application/json**  
  - Returns the same article in XML if **Accept: application/xml**  
  - Returns a plain-text version if **Accept: text/plain**  
  - Returns **406 Not Acceptable** if a unsupported format is requested.
  - By default, articles are returned in JSON format if no `Accept` header is specified.
    

```java
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    //simulated news data
    private static final Map<Integer, String> articles = Map.of(
        1, "Breaking News: Semester 1 Exam Results are due by Feb 6th....",
        2, "Tech News: Students agree not to use Gen AI for this Lab Exercise  .... ",
        3, "Football: Arsenal inch closer to Premier League title...."
    );

   /*
    This endpoint supports multiple response formats (JSON, XML, Plain Text).
    It automatically determines the response format based on the Accept header sent by the client.
    Spring checks the produces attribute to match the best available format.
   */
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> getNewsArticle(
            @PathVariable int id, 
            @RequestHeader(value = "Accept", defaultValue = MediaType.APPLICATION_JSON_VALUE) String acceptHeader) {

        //if the article isn't found return a 404
        if (!articles.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 - News Article Not Found");
        }

        //get the articles content from the map
        String news = articles.get(id);

        //creates a Map containing the requested article's ID and content. This map is used as the response body
        Map<String, Object> response = Map.of("id", id, "article", news);

        //Return JSON if the acceptHEader is "APPLICATION_JSON_VALUE" 
        if (acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity.ok().body(response);
        }

        //Return XML automatically using Jackson if the acceptHEader is "APPLICATION_XML_VALUE" 
        if (acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(response);
        }

        //TODO: Return plain text if Accept header is "text/plain"

        //If no acceptable format is found, return 406 Not Acceptable
        return ResponseEntity.status(406).body("406 - Not Acceptable");
    }
}

```
#### A word on [ResponseEntity](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html)

`ResponseEntity<T>` is a **Spring Boot class** used to customise HTTP responses, including:
- **Status Codes** (`200 OK`, `404 Not Found`, `500 Internal Server Error` etc.)
- **Response Headers** (`Location`, `Cache-Control`, etc.)
- **Response Body** (JSON, XML, text, or nothing)

The endpoint should return `ResponseEntity<?>` when the return type may be different depending on the response scenario (e.g., JSON, error messages, or empty responses).  
  
The endpoint should return `ResponseEntity<Void>` when no response body is needed.

<br>Use `.body(T body)` to set a Response Body. E.G.

```java
@GetMapping("/user")
public ResponseEntity<Map<String, String>> getUser() {
    Map<String, String> user = Map.of("name", "John Doe", "role", "Admin");
    return ResponseEntity.ok().body(user);
}
```
produces:

```json
{
    "name": "John Doe",
    "role": "Admin"
}
```
<br>Use `.build()` when only the status and headers matter (e.g., redirects, 204 No Content).

```java
@GetMapping("/endpoint")
public ResponseEntity<Void> temporaryResponse() {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE) //503 Service Unavailable
            .header("Retry-After", "300") //Suggests the client retrys after 300secs (5mins)
            .build();
}
```
produces:  
```
HTTP/1.1 503 Service Unavailable
Retry-After: 300

(no body)

```

### Test the endpoint in Postman

| ![image](https://github.com/user-attachments/assets/e214cf04-31ed-4f62-851d-2b3b556fe901) |
|:--------------------------------------------------------------------------------------:|
| **Retrieve article #1 in XML format by specifying the accept header as application/xml** |

<br>

| ![image](https://github.com/user-attachments/assets/a1256eda-3b8d-40e2-bffa-db3a7bbba94b) |
|:--------------------------------------------------------------------------------------:|
| **Retrieve article #2 in text by specifying the accept header as text/plain** |

<br>

| ![image](https://github.com/user-attachments/assets/db297683-f433-46c1-84f1-5c36b53ab458) |
|:--------------------------------------------------------------------------------------:|
| **Retrieve article #3 in JSON format by specifying the Accept header as application/json** |

<br>

| ![image](https://github.com/user-attachments/assets/e0d62d6e-e6e0-49f0-be1f-b540e3581581)   |
|:--------------------------------------------------------------------------------------:|
| **Trying to retrieve an article that does not exist. The server responds with a 404 error** |

<br>

| ![image](https://github.com/user-attachments/assets/3f6f2b2c-ec2c-4d9f-9805-fc3f4b082ef0) |
|:--------------------------------------------------------------------------------------:|
| **Try to retrieve article #2 while setting the Accept header to application/x-yml, which is not (yet) supported. The server responds with a 406 Not Acceptable status code.** |

<br>

| ![image](https://github.com/user-attachments/assets/53e70fbc-d268-40a2-9b25-b41fce49bb32) |
|:--------------------------------------------------------------------------------------:|
| **Trying to retrieve article #2 without setting the Accept header. The server responds with the default format - JSON** |

<br>


## Task Two : Custom Error Handling in a Banking API
Your banking API allows customers to check their account balance, but if an account number is not found or has an invalid format, the response should include an error messages.

- Create an endpoint (`/api/balance/{accountNumber}`) that:
  - Returns `400 Bad Request` for invalid account numbers.
  - Returns `404 Not Found` if the account does not exist.
  - Returns `200 OK` with balance details for valid requests.

```java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    //Simulated account balances
    private static final Map<String, Double> accountBalances = Map.of(
        "12345", 1000.50,
        "67890", 250.75
    );

    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<Object> getBalance(@PathVariable String accountNumber) {

        //Validate if the account number contains only digits and is exactly 5 digits long
        if (!accountNumber.matches("\\d{5}")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", "400 - Invalid Account Number Format: " + accountNumber
            ));
        }

        //TODO: Check if the account exists in accountBalances map
        //If not found, return 404 Not Found

        //TODO: If account exists, return 200 OK with balance details
        return ResponseEntity.status(500).body("Not implemented"); //This is a placeholder
    }
}
```
| ![image](https://github.com/user-attachments/assets/119a4ee6-c28f-4fff-9401-53da681ae04f) |
|:--------------------------------------------------------------------------------------:|
| **Retrieve account details for account #12345** |

<br>

|![image](https://github.com/user-attachments/assets/4d560e2a-9e36-4e2e-ae93-eb5ee1262683)|
|:--------------------------------------------------------------------------------------:|
| **Server returns a 400 Bad Request for an invalid bank account number** |

<br>

|![image](https://github.com/user-attachments/assets/a4f8afd2-b011-4192-bb81-8a3dfa57a8d5) |
|:--------------------------------------------------------------------------------------:|
| **Server returns a 404 Not Found for a bank account number that doesnt not exist in the system** |

<br>


 
## Task Three: Redirecting Old URLs to New URLs
Your company has changed its URL structure, and old endpoints must redirect to new ones.
- Implement an endpoint (`/api/old-users/{id}`) that:
  - Redirects permanently (301) to `/api/users/{id}`.
  - Implement a temporary redirect (302) for an endpoint under maintenance (`/api/maintenance`)
  
```java
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RedirectController {

  /*
  When performing redirects, you typically don’t return a response body — just a `location` header that tells the client where to go next.
  Using ResponseEntity<Void> explicitly indicates that the response body is empty.
  It helps ensure the client only follows the location header without expecting content.
 */

  @GetMapping("/old-users/{id}")
    public ResponseEntity<Void> redirectToNewUsers(@PathVariable int id) {
        //TODO: Implement a permanent redirect (301 Moved Permanently) to "/api/users/{id}"
        /*
          - Do NOT use "redirect:" as this is not a server-side redirect.
          - Instead, set the HTTP status to HttpStatus.MOVED_PERMANENTLY (301).
          - Add a "Location" header pointing to the new URL ("/api/users/{id}").
          - Ensure no response body is included using ResponseEntity<Void>.
        */

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build(); //This si a placeholder
    }

    @GetMapping("/maintenance")
    public ResponseEntity<Void> temporaryRedirect() {
        //TODO: Implement a temporary redirect (302 Found) to the maintenance page "/api/maintenance-info"

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build(); //Another placeholder
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<String> newUserEndpoint(@PathVariable int id) {
        return ResponseEntity.ok("Welcome to the new user profile for user ID: " + id);
    }

    @GetMapping("/maintenance-info")
    public ResponseEntity<String> maintenanceInfo() {
        return ResponseEntity.ok("The system is under maintenance. Please try again later.");
    }
}

```



Before testing this task in Postman you will need to disable `Auto-Redirect`. Go to Postman settings (File > Settings > General) and turn off "Automatically follow redirects".

| ![image](https://github.com/user-attachments/assets/f9233f38-da77-426d-83c8-9983f5d494c9) |
|:--------------------------------------------------------------------------------------:|
| **Navigate to /api/old-users/{id}. The server will return a `301 Moved Permanently` status, with the `location` response header specifying the new URL.** | 

<br>

| ![image](https://github.com/user-attachments/assets/f8d2c480-d7e6-42fa-bdc7-4bd414c911b3) |
|:--------------------------------------------------------------------------------------:|
| **Navigate to /api/maintenance. The server responds with a status `302 Found`. The `location` response header will contain the updated url** | 

<br>

If you test these endpoints in a browser, you will be automatically redirected to the URL specified in the `location` header. Browsers automatically follow `301 Moved Permanently` (and other redirect status codes like `302 Found` and `307 Temporary Redirect`) to enhance the UX.


## Task Four: Modifying Response Headers in a User Profile API. 

Create an API endpoint that returns user profile information while applying security modifications to the response headers and masking sensitive data.

- Implement an endpoint (`/users/{username}`) that should return user profile details in JSON format.
  - Mask the email address (e.g., johndoe@example.com → j****@example.com) (_show only the first letter and the domain_).  
  - Include a security header (X-Security-Policy) to enforce best practices.
  - Set cache control headers to prevent storing sensitive user data.

| Header Name         | Value                                          | Purpose                        | Standard/Custom Header |
| ------------------- | ---------------------------------------------- |------------------------------- |------------------------|
| X-Security-Policy	  | No-Cache, Secure                               | Prevents sensitive data caching| Custom                 |
| Cache-Control	      | no-store, no-cache, must-revalidate, max-age=0 |Disables storing user data      | Standard               |
| X-Request-ID        | Generate a unique tracking ID                  | Helps track API requests       | Custom                 |

_Headers prefixed with X- are usually custom headers, as in they are not defined by HTTP specifications and you create them yourself ._

```java
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    //Simulated users being stored in a db
    private static final Map<String, Map<String, String>> users = Map.of(
        "john_doe", Map.of("fullName", "John Doe", "email", "johndoe@example.com", "createdAt", "2022-05-01"),
        "jane_smith", Map.of("fullName", "Jane Smith", "email", "janesmith@example.com", "createdAt", "2023-02-10")
    );

    @GetMapping("/{username}")
    public ResponseEntity<Map<String, String>> getUserProfile(@PathVariable String username) {
        
        //TODO: Check if the user exists, return 404 if not found

        //TODO: Mask the email address (show only the first letter and domain)

        //Generate a unique request ID for tracking
        String requestId = UUID.randomUUID().toString();

        //TODO: Add custom headers:
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Blah-Blah-Blah-Header-Name", "Blah Header Value");
        //- X-Security-Policy: No-Cache, Secure
        //- Cache-Control: no-store, no-cache, must-revalidate, max-age=0
        //- X-Request-ID: Generated unique request ID

        //TODO: Return 200 OK with modified headers and user details (inc masked email address)
        //return ResponseEntity.ok()
        //         .headers(headers)
        //         .body(Map.of(
        //               "username", username,
        //         ));

        return ResponseEntity.status(500).body(null); //placeholder response
    }

 public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email; //Return as this is an invalid email address
        }

        String[] parts = email.split("@");
        String localPart = parts[0]; //Username before '@'
        String domain = parts[1]; //Domain after '@'

        //Ensure at least 1 visible character, mask the rest
        String maskedLocal = localPart.charAt(0) + "****";

        return maskedLocal + "@" + domain;
    }
}

```

| ![image](https://github.com/user-attachments/assets/d131ebf6-ab20-4e15-840d-5ec74cd730bf)|
|:--------------------------------------------------------------------------------------:|
| **Status `200 OK` returned. Examining the response body** | 

<br>

| ![image](https://github.com/user-attachments/assets/a1429c52-056c-45dc-a1c8-55793f4c6688)|
|:--------------------------------------------------------------------------------------:|
| **(Same request) Status `200 OK` returned. Examining the response headers** | 


<br>

| ![image](https://github.com/user-attachments/assets/c00c6f99-a281-4073-b78a-2853dfab4130)|
|:--------------------------------------------------------------------------------------:|
| **User Not Found** | 


