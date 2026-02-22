package com.main.validation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationAdvice {

    //RFC 7807 (an Internet standard that defines a uniform, structured way to represent errors in HTTP API) problem type identifier.
    //This is a stable, machine-readable identifier for the category of error.
    //It is NOT required to be a real or navigable URL; it simply acts as a unique name
    //that clients can use to distinguish validation errors from other error types.
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
        pd.setTitle("Validation failed");       //short, human-readable summary
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


}
