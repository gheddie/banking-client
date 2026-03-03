package de.gravitex.banking.client;

import de.gravitex.banking.client.gui.action.CreateBookingOverviewActualMonthTableContextAction;
import de.gravitex.banking.client.gui.action.CreateBookingOverviewFromBookingTableContextAction;
import de.gravitex.banking.client.gui.action.EditBookingViewTableContextAction;
import de.gravitex.banking.client.gui.action.EditTableContextAction;
import de.gravitex.banking.client.gui.action.factory.ActionFactory;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.TradingPartner;
import de.gravitex.banking_core.entity.view.BookingView;

public class BankingClientMain {
	
	public static void main(String[] args) {
		registerActions();
		runClient();
	}
	
	private static void registerActions() {
		
		ActionFactory actionFactory = ApplicationRegistry.getInstance().getActionFactory();

		// Account
		actionFactory.registerAction(Account.class, EditTableContextAction.class);
		
		// TradingPartner
		actionFactory.registerAction(TradingPartner.class, EditTableContextAction.class);
		
		// BookingView
		actionFactory.registerAction(BookingView.class, CreateBookingOverviewFromBookingTableContextAction.class);
		actionFactory.registerAction(BookingView.class, CreateBookingOverviewActualMonthTableContextAction.class);
		actionFactory.registerAction(BookingView.class, EditBookingViewTableContextAction.class);
	}

	private static void runClient() {
		BankingClient bankingClient = new BankingClient();
		ApplicationRegistry.getInstance().setParentView(bankingClient);
		bankingClient.onStartUp().setVisible(true);
	}
}