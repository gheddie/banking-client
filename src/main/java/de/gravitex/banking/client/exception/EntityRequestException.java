package de.gravitex.banking.client.exception;

import de.gravitex.banking.client.accessor.HttpRequestBuilder;

public class EntityRequestException extends Exception {
	
	private static final long serialVersionUID = 4421273141893315579L;
	
	private HttpRequestBuilder requestBuilder;

	public EntityRequestException(String message, Throwable aThrowable, HttpRequestBuilder aRequestBuilder) {
		super(message, aThrowable);
		this.requestBuilder = aRequestBuilder;
	}

	public String buildMessage() {
		return "Fehler beim Abfragen von Entities von Typ [" + requestBuilder.getEntityClass().getSimpleName()
				+ "], URL: " + requestBuilder.buildRequestUrl() + ", Fehler: " + getMessage();
	}
}