package de.gravitex.banking.client.exception;

public class CrudException extends Exception {
	
	private static final long serialVersionUID = 3923364851376195136L;

	public CrudException(String message, Throwable t) {
		super(message, t);
	}
}