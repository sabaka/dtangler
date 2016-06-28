package org.dtangler.core;

public class MissingArgumentsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MissingArgumentsException() {
		super("MissingArgumentsException", null);
	}

	public MissingArgumentsException(String message) {
		super("MissingArgumentsException: " + message);
	}

	public MissingArgumentsException(String message, Throwable cause) {
		super("MissingArgumentsException: " + message, cause);
	}
}
