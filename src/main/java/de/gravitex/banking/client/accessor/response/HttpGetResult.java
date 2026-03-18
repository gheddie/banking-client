package de.gravitex.banking.client.accessor.response;

import java.util.List;

import de.gravitex.banking.client.accessor.request.HttpRequestType;
import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.client.accessor.response.util.HttpResultListener;
import de.gravitex.banking.client.accessor.response.util.RequestDuration;
import de.gravitex.banking.client.accessor.response.util.StreamTraffic;
import de.gravitex.banking.client.accessor.util.HttpActionType;

public class HttpGetResult extends HttpResult {

	private List<?> entityList;

	private Object entity;

	public HttpGetResult(int aStatusCode, String aErrorMessage, Object aEntity, String aRequestUrl,
			RequestDuration aRequestDuration, StreamTraffic aUpstreamBytes, StreamTraffic aDownstreamBytes) {
		super(aStatusCode, aErrorMessage, aRequestUrl, aRequestDuration, aUpstreamBytes, aDownstreamBytes);
		this.entity = aEntity;
	}

	public HttpGetResult(int aStatusCode, String aErrorMessage, List<?> aEntityList, String aRequestUrl,
			RequestDuration aRequestDuration, StreamTraffic aUpstreamBytes, StreamTraffic aDownstreamBytes) {
		super(aStatusCode, aErrorMessage, aRequestUrl, aRequestDuration, aUpstreamBytes, aDownstreamBytes);
		this.entityList = aEntityList;
	}

	public List<?> getEntityList() {
		return entityList;
	}

	public Object getEntity() {
		return entity;
	}

	@Override
	public HttpRequestType getRequestType() {
		return HttpRequestType.GET;
	}

	@Override
	public void cacheRequestResult(HttpResultListener aHttpResultListener, String aVariableName) {
		if (entityList != null) {
			aHttpResultListener.acceptListResult(entityList, aVariableName);
		} else if (entity != null) {
			aHttpResultListener.acceptObjectResult(entity, aVariableName);
		}
	}

	@Override
	public String formatResponseContext() {
		if (entityList != null) {
			return formatObjectList(entityList);
		} else if (entity != null) {
			return formatSingleObject(entity);
		}
		return "";
	}

	@Override
	public HttpActionType getActionType() {
		return HttpActionType.CRUD;
	}
	
	@Override
	public int getActualResponseLength() {
		return entityList.size();
	}
}