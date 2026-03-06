package de.gravitex.banking.client.accessor.response;

import de.gravitex.banking.client.accessor.response.base.HttpResult;

public class HttpPostResult extends HttpResult {

	public HttpPostResult(int aStatusCode, String aErrorMessage) {
		super(aStatusCode, aErrorMessage);
	}

	public HttpPostResult() {
		super();
	}
}