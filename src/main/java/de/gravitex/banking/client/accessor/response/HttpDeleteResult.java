package de.gravitex.banking.client.accessor.response;

import de.gravitex.banking.client.accessor.request.HttpRequestType;
import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.client.accessor.response.util.HttpResultListener;

public class HttpDeleteResult extends HttpResult {

	public HttpDeleteResult(int aStatusCode, String aErrorMessage, String aRequestUrl) {
		super(aStatusCode, aErrorMessage, aRequestUrl);
	}

	@Override
	public HttpRequestType getRequestType() {
		return HttpRequestType.DELETE;
	}

	@Override
	public void cacheRequestResult(HttpResultListener aHttpResultListener, String aVariableName) {
		// TODO Auto-generated method stub
	}

	@Override
	public String formatResponseContext() {
		return "[keine Antwort]";
	}
}