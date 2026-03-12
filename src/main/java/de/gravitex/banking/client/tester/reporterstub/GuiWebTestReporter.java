package de.gravitex.banking.client.tester.reporterstub;

import java.util.ArrayList;
import java.util.List;

import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.client.gui.dialog.webtest.GuiWebTestReporterResultDialog;
import de.gravitex.banking.client.tester.reporterstub.base.WebTestReporterStub;
import de.gravitex.banking.client.tester.reporterstub.util.HttpResultWrapper;

public class GuiWebTestReporter extends WebTestReporterStub {

	private List<HttpResultWrapper> wrappers = new ArrayList<>();

	@Override
	public void acceptSuccess(HttpResult aHttpResult, boolean aShouldSuceed) {
		wrappers.add(HttpResultWrapper.forData(aHttpResult, aShouldSuceed));
	}

	@Override
	public void onTestSucceed() {
		new GuiWebTestReporterResultDialog(wrappers).setVisible(true);
	}
}