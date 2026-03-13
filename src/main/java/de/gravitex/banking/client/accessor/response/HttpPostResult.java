package de.gravitex.banking.client.accessor.response;

import de.gravitex.banking.client.accessor.request.HttpRequestType;
import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.client.accessor.response.util.HttpResultListener;
import de.gravitex.banking.client.accessor.response.util.RequestDuration;
import de.gravitex.banking.client.accessor.response.util.StreamTraffic;
import de.gravitex.banking.client.accessor.util.HttpActionType;

public class HttpPostResult extends HttpResult {

	private Object responseObject;

	public HttpPostResult(int aStatusCode, String aErrorMessage, Object aResponseObject, String aRequestUrl,
			RequestDuration aRequestDuration, StreamTraffic aUpstreamBytes, StreamTraffic aDownstreamBytes) {
		super(aStatusCode, aErrorMessage, aRequestUrl, aRequestDuration, aUpstreamBytes, aDownstreamBytes);
		this.responseObject = aResponseObject;
	}

	public Object getResponseObject() {
		return responseObject;
	}

	@Override
	public HttpRequestType getRequestType() {
		return HttpRequestType.POST;
	}

	@Override
	public void cacheRequestResult(HttpResultListener aHttpResultListener, String aVariableName) {
		// TODO Auto-generated method stub
	}

	@Override
	public String formatResponseContext() {
		if (responseObject == null) {
			return "";
		}
		return formatSingleObject(responseObject);
	}

	@Override
	public HttpActionType getActionType() {
		return HttpActionType.ACTION;
	}
}