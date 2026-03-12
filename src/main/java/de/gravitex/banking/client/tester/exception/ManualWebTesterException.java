package de.gravitex.banking.client.tester.exception;

public class ManualWebTesterException extends RuntimeException {

	private static final long serialVersionUID = 2434659558858310340L;
	
	public ManualWebTesterException(String message, Throwable t) {
		super(message, t);
	}

	public ManualWebTesterException(String message) {
		this(message, null);
	}
}