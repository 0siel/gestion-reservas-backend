package com.example.demo.exceptions;

public class RecursoEnUsoException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecursoEnUsoException(String message) {
        super(message);
    }
}