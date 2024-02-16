package org.vladstasyshyn.githubapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class GithubApiControllerExceptionHandler {

    @ExceptionHandler(WebClientResponseException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<GithubApiControllerExceptionResponse> handleContactNotFoundException(WebClientResponseException.NotFound exception) {

        var response = new GithubApiControllerExceptionResponse(HttpStatus.NOT_FOUND.toString(), exception.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
