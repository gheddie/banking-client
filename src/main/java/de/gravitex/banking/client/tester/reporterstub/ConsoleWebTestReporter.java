package de.gravitex.banking.client.tester.reporterstub;

import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.reporterstub.base.WebTestReporterStub;
import de.gravitex.banking_core.util.StringHelper;

public class ConsoleWebTestReporter extends WebTestReporterStub {

	@Override
	public void onTestSucceed(ManualWebTester aManualWebTester) {

	}

	@Override
	public void acceptSuccess(HttpResult aHttpResult, boolean aShouldSuceed, boolean aTraceEnabled) {
		String preparedMessage = prepareMessage(aHttpResult, aShouldSuceed);
		String formattedContext = aHttpResult.formatResponseContext();
		if (!(StringHelper.isBlank(formattedContext))) {
			System.out.println(preparedMessage + " [Context --> "+formattedContext+"]");
		} else {
			System.out.println(preparedMessage);
		}
		increaseSuccessCounter();	
	}
}