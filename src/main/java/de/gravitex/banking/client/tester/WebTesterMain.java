package de.gravitex.banking.client.tester;

import de.gravitex.banking.client.gui.action.CopyPayloadDownstreamAction;
import de.gravitex.banking.client.gui.action.CopyPayloadUpstreamAction;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.client.tester.executor.ManualWebTesterExecutor;
import de.gravitex.banking.client.tester.instance.ImportBookingFilesManualWebTester;
import de.gravitex.banking.client.tester.reporterstub.util.HttpResultWrapper;

public class WebTesterMain {

	public static void main(String[] args) {

		ApplicationRegistry.getInstance().getActionFactory().registerAction(HttpResultWrapper.class,
				CopyPayloadUpstreamAction.class);
		ApplicationRegistry.getInstance().getActionFactory().registerAction(HttpResultWrapper.class,
				CopyPayloadDownstreamAction.class);

		new ManualWebTesterExecutor().runForInstance(ImportBookingFilesManualWebTester.class);
		// new ManualWebTesterExecutor().runForInstance(AttachRecurringPositionToTradingPartnerManualWebTester.class);
	}
}