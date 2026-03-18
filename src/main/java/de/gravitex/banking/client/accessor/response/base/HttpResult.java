package de.gravitex.banking.client.accessor.response.base;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.gravitex.banking.client.accessor.request.HttpRequestType;
import de.gravitex.banking.client.accessor.response.util.HttpResultListener;
import de.gravitex.banking.client.accessor.response.util.RequestDuration;
import de.gravitex.banking.client.accessor.response.util.StreamTraffic;
import de.gravitex.banking.client.accessor.util.HttpActionType;
import de.gravitex.banking_core.util.StringHelper;

public abstract class HttpResult {

	protected static final int UNDEFINED_RESPONSE_CODE = -1;

	private int statusCode;

	private String errorMessage;

	private String requestUrl;

	private RequestDuration requestDuration;

	private StreamTraffic upstreamBytes;

	private StreamTraffic downstreamBytes;

	public HttpResult(int aStatusCode, String aErrorMessage, String aRequestUrl, RequestDuration aRequestDuration,
			StreamTraffic aUpstreamBytes, StreamTraffic aDownstreamBytes) {

		super();

		this.statusCode = aStatusCode;
		this.errorMessage = aErrorMessage;
		this.requestUrl = aRequestUrl;
		this.requestDuration = aRequestDuration;

		this.upstreamBytes = aUpstreamBytes;
		this.downstreamBytes = aDownstreamBytes;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public boolean hasValidStatusCode() {
		return String.valueOf(statusCode).startsWith("2");
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public abstract HttpRequestType getRequestType();

	public abstract void cacheRequestResult(HttpResultListener aHttpResultListener, String aVariableName);

	public abstract String formatResponseContext();

	@Override
	public String toString() {
		return requestUrl;
	}

	protected String formatSingleObject(Object aObject) {
		if (aObject == null) {
			return "";
		}
		return "Objekt vom Typ {" + aObject.getClass().getSimpleName() + "}";
	}

	protected String formatObjectList(List<?> aEntityList) {
		if (aEntityList == null || aEntityList.isEmpty()) {
			return "";
		}
		Set<String> classes = new HashSet<>();
		for (Object entity : aEntityList) {
			classes.add(entity.getClass().getSimpleName());
		}
		return "Liste mit {" + aEntityList.size() + "} Einträgen [Typen: "
				+ StringHelper.seperateList(classes.toArray(new String[classes.size()]), ",") + "]";
	}

	public abstract HttpActionType getActionType();

	public RequestDuration getRequestDuration() {
		return requestDuration;
	}
	
	public StreamTraffic getUpstreamBytes() {
		return upstreamBytes;
	}
	
	public StreamTraffic getDownstreamBytes() {
		return downstreamBytes;
	}

	public abstract int getActualResponseLength();
}