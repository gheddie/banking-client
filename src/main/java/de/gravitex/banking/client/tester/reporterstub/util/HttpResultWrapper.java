package de.gravitex.banking.client.tester.reporterstub.util;

import de.gravitex.banking.client.accessor.request.HttpRequestType;
import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.client.accessor.response.util.RequestDuration;
import de.gravitex.banking.client.accessor.response.util.StreamTraffic;
import de.gravitex.banking.client.accessor.util.HttpActionType;
import de.gravitex.banking.entity.annotation.PresentMe;

public class HttpResultWrapper {
	
	@PresentMe(order = 5)
	private HttpActionType actionType;
	
	@PresentMe(order = 7)
	private RequestDuration requestDuration;
	
	@PresentMe(order = 10)
	private HttpRequestType requestType;
	
	@PresentMe(order = 20)
	private HttpResult httpResult;
	
	@PresentMe(order = 30)
	private int statusCode;
	
	@PresentMe(order = 40)
	private String errorMessage;
	
	@PresentMe(order = 50)
	private boolean shouldSuceed;

	@PresentMe(order = 60)
	private String responseContext;

	@PresentMe(order = 70)
	private StreamTraffic upstreamBytes;

	@PresentMe(order = 80)
	private StreamTraffic downstreamBytes;

	private HttpResultWrapper(HttpResult aHttpResult, boolean aShouldSuceed) {
		
		super();
		
		this.requestType = aHttpResult.getRequestType();
		this.httpResult = aHttpResult;
		this.errorMessage = aHttpResult.getErrorMessage();
		this.statusCode = httpResult.getStatusCode();
		this.shouldSuceed = aShouldSuceed;
		this.responseContext = aHttpResult.formatResponseContext();
		this.actionType = aHttpResult.getActionType();
		this.requestDuration = aHttpResult.getRequestDuration();
		
		this.upstreamBytes = aHttpResult.getUpstreamBytes();
		this.downstreamBytes = aHttpResult.getDownstreamBytes();
	}

	public static HttpResultWrapper forData(HttpResult aHttpResult, boolean aShouldSuceed) {
		return new HttpResultWrapper(aHttpResult, aShouldSuceed);
	}
}