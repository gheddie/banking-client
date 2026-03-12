package de.gravitex.banking.client.exception;

import java.net.ConnectException;

import de.gravitex.banking.client.accessor.HttpRequestBuilder;

public class BackEndNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = -5020609130447173078L;

	public BackEndNotAvailableException(ConnectException aConnectException, HttpRequestBuilder aRequestBuilder) {
		super(buildMessage(aRequestBuilder));
	}

	private static String buildMessage(HttpRequestBuilder aRequestBuilder) {
		return "Backend nicht erreichbar für Request {" + aRequestBuilder.buildRequestUrl() + "}!!!";
	}
}