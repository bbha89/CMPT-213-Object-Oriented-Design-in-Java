package ca.cmpt213.a3.onlinesuperherotracker.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class with error 404
 * Brandon Ha, 301333647, bbha@sfu.ca
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Request ID not found.")
public class SuperheroNotFoundException extends RuntimeException{

}

//source: https://stackoverflow.com/questions/25422255/how-to-return-404-response-status-in-spring-boot-responsebody-method-return-t