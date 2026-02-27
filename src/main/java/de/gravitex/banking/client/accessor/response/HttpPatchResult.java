package de.gravitex.banking.client.accessor.response;

import de.gravitex.banking_core.util.StringHelper;

public class HttpPatchResult {

	private static final int UNDEFINED_RESPONSE_CODE = -1;

	private static final String DEFAULT_ERROR_MESSAGE = "Fehler";
	
	private int statusCode;

	private String errorMessage;

	public HttpPatchResult(int aStatusCode, String aErrorMessage) {
		super();
		this.statusCode = aStatusCode;
		this.errorMessage = aErrorMessage;
	}
	
	public HttpPatchResult() {
		this(UNDEFINED_RESPONSE_CODE, null);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public boolean hasValidStatusCode() {
		return String.valueOf(statusCode).startsWith("2");
	}

	public String getErrorMessage() {
		if (!StringHelper.isBlank(errorMessage)) {
			return errorMessage;	
		}
		return DEFAULT_ERROR_MESSAGE;
	}
}