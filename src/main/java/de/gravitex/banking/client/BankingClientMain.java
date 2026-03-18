package de.gravitex.banking.client;

import java.awt.BorderLayout;
import java.util.Arrays;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.gravitex.banking.client.gui.action.CopyPayloadUpstreamAction;
import de.gravitex.banking.client.gui.action.CreateBookingOverviewActualMonthTableContextAction;
import de.gravitex.banking.client.gui.action.CreateBookingOverviewFromBookingTableContextAction;
import de.gravitex.banking.client.gui.action.DeleteTableContextAction;
import de.gravitex.banking.client.gui.action.EditBookingViewTableContextAction;
import de.gravitex.banking.client.gui.action.EditTableContextAction;
import de.gravitex.banking.client.gui.action.ImportBookingsForAccountAction;
import de.gravitex.banking.client.gui.action.ShowBookingCurrentTableContextAction;
import de.gravitex.banking.client.gui.action.factory.ActionFactory;
import de.gravitex.banking.client.gui.test.FPS1Rows;
import de.gravitex.banking.client.gui.test.FPS2Rows;
import de.gravitex.banking.client.gui.test.FPS3Rows;
import de.gravitex.banking.client.gui.test.FPS4Rows;
import de.gravitex.banking.client.gui.test.FpsMoo;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.client.tester.reporterstub.util.HttpResultWrapper;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.PurposeCategory;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking_core.entity.view.BookingView;

public class BankingClientMain {

	private static final String LOOK_AND_FEEL = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

	public static void main(String[] args) {
		
		doRunClient();

		// testGridbagFilterPanelLayout();
		
		/*
		BookingProgress bp = new BookingProgress();
		bp.setStartDate(LocalDate.of(2000, 1, 1));
		bp.setEndDate(LocalDate.of(2030, 12, 31));
		bp.setTradingPartners((List<TradingPartner>) new BankingAccessor().readTradingPartners().getEntityList());
		writeJsonObject(bp);
		*/
	}

	private static void writeJsonObject(Object object) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			System.out.println(objectMapper.writeValueAsString(object));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private static void doRunClient() {
		registerActions();
		runClient();
	}

	private static void registerActions() {

		ActionFactory actionFactory = ApplicationRegistry.getInstance().getActionFactory();

		// CreditInstitute
		actionFactory.registerAction(CreditInstitute.class, DeleteTableContextAction.class);

		// Account
		actionFactory.registerAction(Account.class, EditTableContextAction.class);
		actionFactory.registerAction(Account.class, ImportBookingsForAccountAction.class);

		// TradingPartner
		actionFactory.registerAction(TradingPartner.class, EditTableContextAction.class);
		actionFactory.registerAction(TradingPartner.class, DeleteTableContextAction.class);
		actionFactory.registerAction(TradingPartner.class, ShowBookingCurrentTableContextAction.class);

		// BookingView
		actionFactory.registerAction(BookingView.class, CreateBookingOverviewFromBookingTableContextAction.class);
		actionFactory.registerAction(BookingView.class, CreateBookingOverviewActualMonthTableContextAction.class);
		actionFactory.registerAction(BookingView.class, EditBookingViewTableContextAction.class);
		actionFactory.registerAction(BookingView.class, DeleteTableContextAction.class);

		// PurposeCategory
		actionFactory.registerAction(PurposeCategory.class, DeleteTableContextAction.class);
		actionFactory.registerAction(PurposeCategory.class, EditTableContextAction.class);
		
		// actionFactory.registerAction(HttpResultWrapper.class, CopyPayloadAction.class);
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
	
	private static void testGridbagFilterPanelLayout() {

		JDialog dialog = new JDialog();
		dialog.setSize(800, 600);
		dialog.setLayout(new BorderLayout());

		JTabbedPane tp = new JTabbedPane();

		tp.addTab("1", new FPS1Rows(Arrays.asList(new FpsMoo[] { new FpsMoo() })));
		tp.addTab("2", new FPS2Rows(Arrays.asList(new FpsMoo[] { new FpsMoo(), new FpsMoo() })));
		tp.addTab("3", new FPS3Rows(Arrays.asList(new FpsMoo[] { new FpsMoo(), new FpsMoo(), new FpsMoo() })));
		tp.addTab("4", new FPS4Rows(Arrays.asList(new FpsMoo[] { new FpsMoo(), new FpsMoo(), new FpsMoo(), new FpsMoo() })));

		dialog.add(tp, BorderLayout.CENTER);

		dialog.setVisible(true);
	}
}