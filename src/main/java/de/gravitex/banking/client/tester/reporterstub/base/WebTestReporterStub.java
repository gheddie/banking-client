package de.gravitex.banking.client.tester.reporterstub.base;

import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;

public abstract class WebTestReporterStub {
	
	private int successCounter = 1;

	public abstract void acceptSuccess(HttpResult aHttpResult, boolean aShouldSuceed, boolean aTraceEnabled);

	public abstract void onTestSucceed(ManualWebTester aManualWebTester);
	
	protected String prepareMessage(HttpResult aHttpResult, boolean aShouldSuceed) {
		String message = "[" + successCounter + "] {" + aHttpResult.getRequestType().name() + "} "
				+ aHttpResult.getRequestUrl() + ", Status [" + aHttpResult.getStatusCode() + "] --> OK";
		if (aShouldSuceed) {
			return message + " (expected to SUCEED)";
		} else {
			return message + " (expected to FAIL)";
		}
	}
	
	protected void increaseSuccessCounter() {
		successCounter++;
	}
}