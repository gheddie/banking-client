package de.gravitex.banking.client.exception;

import de.gravitex.banking.client.accessor.HttpRequestBuilder;

public class BankingRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 4421273141893315579L;

	public BankingRequestException(HttpRequestBuilder aRequestBuilder) {
		super(buildMessage(aRequestBuilder));
	}

	private static String buildMessage(HttpRequestBuilder aRequestBuilder) {
		return "unable to request entities of type ["+aRequestBuilder.getEntityClass()+"] for url: " + aRequestBuilder.buildRequestUrl();
	}
}