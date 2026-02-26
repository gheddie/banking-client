package de.gravitex.banking.client.exception;

import de.gravitex.banking.client.accessor.HttpRequestBuilder;

public class BankingRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 4421273141893315579L;

	public BankingRequestException(HttpRequestBuilder aRequestBuilder, Throwable aThrowable) {
		super(buildMessage(aRequestBuilder, aThrowable), aThrowable);
	}

	private static String buildMessage(HttpRequestBuilder aRequestBuilder, Throwable aThrowable) {
		return "unable to request entities of type [" + aRequestBuilder.getEntityClass() + "] for url: "
				+ aRequestBuilder.buildRequestUrl() + " ["+aThrowable.getMessage()+"]";
	}
}