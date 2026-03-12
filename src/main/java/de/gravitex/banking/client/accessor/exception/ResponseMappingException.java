package de.gravitex.banking.client.accessor.exception;

public class ResponseMappingException extends RuntimeException {

	private static final long serialVersionUID = 37778011789911415L;
	
	public ResponseMappingException(String message, Throwable t) {
		super(message, t);
	}
}