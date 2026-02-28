package de.gravitex.banking.client.accessor.response;

import de.gravitex.banking.client.accessor.response.base.HttpResult;

public class HttpDeleteResult extends HttpResult {

	public HttpDeleteResult(int aStatusCode, String aErrorMessage) {
		super(aStatusCode, aErrorMessage);
	}

	public HttpDeleteResult() {
		super();
	}
}