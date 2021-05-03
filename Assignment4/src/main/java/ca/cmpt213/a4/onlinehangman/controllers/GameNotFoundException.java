package ca.cmpt213.a4.onlinehangman.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class with error 404
 * Brandon Ha, 301333647, bbha@sfu.ca
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Game session not found.")
public class GameNotFoundException extends RuntimeException{

}
