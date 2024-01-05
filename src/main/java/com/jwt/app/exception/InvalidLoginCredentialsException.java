package com.jwt.app.exception;

public class InvalidLoginCredentialsException extends Exception {

	public InvalidLoginCredentialsException() {
        super();
    }

    public InvalidLoginCredentialsException(String message) {
        super(message);
    } 
}
