package de.gravitex.banking.client.accessor.response;

import de.gravitex.banking.client.accessor.request.HttpRequestType;
import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.client.accessor.response.util.HttpResultListener;
import de.gravitex.banking.entity.base.IdEntity;

public class HttpPatchResult extends HttpResult {

	private IdEntity responseObject;

	public HttpPatchResult(int aStatusCode, String aErrorMessage, String aRequestUrl, IdEntity aResponseObject) {
		super(aStatusCode, aErrorMessage, aRequestUrl);
		this.responseObject = aResponseObject;
	}
	
	@Override
	public HttpRequestType getRequestType() {
		return HttpRequestType.PATCH;
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
}