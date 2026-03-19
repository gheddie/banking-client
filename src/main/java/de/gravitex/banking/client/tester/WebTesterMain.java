package de.gravitex.banking.client.tester;

import de.gravitex.banking.client.gui.action.CopyPayloadDownstreamAction;
import de.gravitex.banking.client.gui.action.CopyPayloadUpstreamAction;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.client.tester.instance.AttachRecurringPositionToTradingPartnerManualWebTester;
import de.gravitex.banking.client.tester.instance.ImportBookingFilesManualWebTester;
import de.gravitex.banking.client.tester.instance.MergeTradingPartnersManualWebTester;
import de.gravitex.banking.client.tester.reporterstub.util.HttpResultWrapper;
import de.gravitex.banking.client.tester.util.ManualWebTestListWrapper;

public class WebTesterMain {

	public static void main(String[] args) {

		ApplicationRegistry.getInstance().getActionFactory().registerAction(HttpResultWrapper.class,
				CopyPayloadUpstreamAction.class);
		ApplicationRegistry.getInstance().getActionFactory().registerAction(HttpResultWrapper.class,
				CopyPayloadDownstreamAction.class);

		new ManualWebTestListWrapper().withTestClass(ImportBookingFilesManualWebTester.class)
				.withTestClass(MergeTradingPartnersManualWebTester.class)
				.withTestClass(AttachRecurringPositionToTradingPartnerManualWebTester.class).runTests();
	}
}