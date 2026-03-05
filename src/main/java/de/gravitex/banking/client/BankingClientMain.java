package de.gravitex.banking.client;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import de.gravitex.banking.client.gui.action.CreateBookingOverviewActualMonthTableContextAction;
import de.gravitex.banking.client.gui.action.CreateBookingOverviewFromBookingTableContextAction;
import de.gravitex.banking.client.gui.action.EditBookingViewTableContextAction;
import de.gravitex.banking.client.gui.action.EditTableContextAction;
import de.gravitex.banking.client.gui.action.factory.ActionFactory;
import de.gravitex.banking.client.gui.test.FPS1Rows;
import de.gravitex.banking.client.gui.test.FPS2Rows;
import de.gravitex.banking.client.gui.test.FPS3Rows;
import de.gravitex.banking.client.gui.test.FPS4Rows;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.TradingPartner;
import de.gravitex.banking_core.entity.view.BookingView;

public class BankingClientMain {
	
	private static final String LOOK_AND_FEEL = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

	public static void main(String[] args) {
		
		registerActions();
		runClient();
		
		testGridbagFilterPanelLayout();
	}

	private static void testGridbagFilterPanelLayout() {
		
		JDialog dialog = new JDialog();
		dialog.setSize(800, 600);
		dialog.setLayout(new BorderLayout());
		
		JTabbedPane tp = new JTabbedPane();
		tp.addTab("1", new FPS1Rows());
		tp.addTab("2", new FPS2Rows());
		tp.addTab("3", new FPS3Rows());
		tp.addTab("4", new FPS4Rows());
		dialog.add(tp, BorderLayout.CENTER);
		
		dialog.setVisible(true);
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
		
		try {
			// UIManager.setLookAndFeel(LOOK_AND_FEEL);
			BankingClient bankingClient = new BankingClient();
			ApplicationRegistry.getInstance().setParentView(bankingClient);
			bankingClient.onStartUp().setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}