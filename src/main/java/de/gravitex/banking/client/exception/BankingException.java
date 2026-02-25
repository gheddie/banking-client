package de.gravitex.banking.client.exception;

public class BankingException extends Exception {

	private static final long serialVersionUID = 41062016495660967L;
	
	public BankingException(String message, Throwable t) {
		super(message, t);
	}
}