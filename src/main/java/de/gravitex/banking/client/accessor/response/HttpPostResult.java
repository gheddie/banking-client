package de.gravitex.banking.client.accessor.response;

import de.gravitex.banking.client.accessor.response.base.HttpResult;

public class HttpPostResult extends HttpResult {

	private Object responseObject;

	public HttpPostResult(int aStatusCode, String aErrorMessage, Object aResponseObject) {
		super(aStatusCode, aErrorMessage);
		this.responseObject = aResponseObject;
	}

	public HttpPostResult() {
		super();
	}
	
	public Object getResponseObject() {
		return responseObject;
	}
}