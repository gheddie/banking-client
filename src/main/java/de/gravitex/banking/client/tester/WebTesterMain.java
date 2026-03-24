package de.gravitex.banking.client.tester;

import de.gravitex.banking.client.gui.action.CopyPayloadDownstreamAction;
import de.gravitex.banking.client.gui.action.CopyPayloadUpstreamAction;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.client.tester.instance.AccountOverviewManualWebTester;
import de.gravitex.banking.client.tester.instance.AttachRecurringPositionToTradingPartnerManualWebTester;
import de.gravitex.banking.client.tester.instance.DetermineBookingFileDateManualWebTester;
import de.gravitex.banking.client.tester.instance.FailingManualWebTester;
import de.gravitex.banking.client.tester.instance.ImportBookingFilesManualWebTester;
import de.gravitex.banking.client.tester.instance.MergeTradingPartnersManualWebTester;
import de.gravitex.banking.client.tester.instance.ProposeRecurringPositionManualWebTester;
import de.gravitex.banking.client.tester.reporterstub.util.HttpResultWrapper;
import de.gravitex.banking.client.tester.util.ManualWebTestDefinition;
import de.gravitex.banking.client.tester.util.ManualWebTestListWrapper;

public class WebTesterMain {

	public static void main(String[] args) {

		registerActions();
		new ManualWebTestListWrapper(false)
				.withTestDefinition(
						ManualWebTestDefinition.forTestClass(DetermineBookingFileDateManualWebTester.class, true))
				.withTestDefinition(ManualWebTestDefinition.forTestClass(ImportBookingFilesManualWebTester.class, true))
				.withTestDefinition(
						ManualWebTestDefinition.forTestClass(MergeTradingPartnersManualWebTester.class, true))
				.withTestDefinition(ManualWebTestDefinition.forTestClass(FailingManualWebTester.class, false))
				.withTestDefinition(ManualWebTestDefinition
						.forTestClass(AttachRecurringPositionToTradingPartnerManualWebTester.class, true))
				.withTestDefinition(ManualWebTestDefinition.forTestClass(AccountOverviewManualWebTester.class, true))
				.withTestDefinition(ManualWebTestDefinition.forTestClass(ProposeRecurringPositionManualWebTester.class, true))
				.runTests();
	}

	private static void registerActions() {

		ApplicationRegistry.getInstance().getActionFactory().registerAction(HttpResultWrapper.class,
				CopyPayloadUpstreamAction.class);
		ApplicationRegistry.getInstance().getActionFactory().registerAction(HttpResultWrapper.class,
				CopyPayloadDownstreamAction.class);
	}
}