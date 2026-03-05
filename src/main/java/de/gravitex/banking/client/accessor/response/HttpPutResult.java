package de.gravitex.banking.client.accessor.response;

import de.gravitex.banking.client.accessor.response.base.HttpResult;

public class HttpPutResult extends HttpResult {

	public HttpPutResult(int aStatusCode, String aErrorMessage) {
		super(aStatusCode, aErrorMessage);
	}
	
	public HttpPutResult() {
		super();
	}
}