package de.gravitex.banking.client;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.gravitex.banking.client.accessor.BankingAccessor;
import de.gravitex.banking.client.gui.action.CreateBookingOverviewActualMonthTableContextAction;
import de.gravitex.banking.client.gui.action.CreateBookingOverviewFromBookingTableContextAction;
import de.gravitex.banking.client.gui.action.DeleteTableContextAction;
import de.gravitex.banking.client.gui.action.EditBookingViewTableContextAction;
import de.gravitex.banking.client.gui.action.EditTableContextAction;
import de.gravitex.banking.client.gui.action.ImportBookingsForAccountAction;
import de.gravitex.banking.client.gui.action.factory.ActionFactory;
import de.gravitex.banking.client.gui.test.FPS1Rows;
import de.gravitex.banking.client.gui.test.FPS2Rows;
import de.gravitex.banking.client.gui.test.FPS3Rows;
import de.gravitex.banking.client.gui.test.FPS4Rows;
import de.gravitex.banking.client.gui.test.FpsMoo;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.PurposeCategory;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking_core.dto.BudgetPlanningDto;
import de.gravitex.banking_core.dto.MergeTradingPartners;
import de.gravitex.banking_core.entity.view.BookingView;

public class BankingClientMain {

	private static final String LOOK_AND_FEEL = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

	public static void main(String[] args) {

		doRunClient();

		// testGridbagFilterPanelLayout();

		// testReadBudgetPlanning();
		
		// testMergeTradingPartners();
		
		// manualTest();
	}

	private static void manualTest() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(mapper.writeValueAsString(new Account()));			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static void testMergeTradingPartners() {
		
		List<TradingPartner> allTradingPartners = (List<TradingPartner>) new BankingAccessor().readTradingPartners(null)
				.getEntityList();
		List<TradingPartner> toMerge = new ArrayList<>();
		for (TradingPartner aTradingPartner : allTradingPartners) {
			if (aTradingPartner.getTradingKey().contains("MCD")) {
				toMerge.add(aTradingPartner);
			}
		}

		try {
			MergeTradingPartners merge = new MergeTradingPartners();
			merge.setNewTradingKey("MCD_CONCATTED");
			merge.setPartnersToMerge(toMerge);
			System.out.println(new ObjectMapper().writeValueAsString(merge));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void doRunClient() {
		registerActions();
		runClient();
	}

	private static void testReadBudgetPlanning() {

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			BudgetPlanningDto bpd = mapper.readValue(new File("C:\\tmp\\yaml\\bptest.yaml"), BudgetPlanningDto.class);
			System.out.println(new ObjectMapper().writeValueAsString(bpd));
			int werner = 5;
		} catch (IOException e) {
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

	private static void registerActions() {

		ActionFactory actionFactory = ApplicationRegistry.getInstance().getActionFactory();

		// CreditInstitute
		actionFactory.registerAction(CreditInstitute.class, DeleteTableContextAction.class);

		// Account
		actionFactory.registerAction(Account.class, EditTableContextAction.class);
		actionFactory.registerAction(Account.class, ImportBookingsForAccountAction.class);

		// TradingPartner
		actionFactory.registerAction(TradingPartner.class, EditTableContextAction.class);

		// BookingView
		actionFactory.registerAction(BookingView.class, CreateBookingOverviewFromBookingTableContextAction.class);
		actionFactory.registerAction(BookingView.class, CreateBookingOverviewActualMonthTableContextAction.class);
		actionFactory.registerAction(BookingView.class, EditBookingViewTableContextAction.class);
		actionFactory.registerAction(BookingView.class, DeleteTableContextAction.class);

		// PurposeCategory
		actionFactory.registerAction(PurposeCategory.class, DeleteTableContextAction.class);
		actionFactory.registerAction(PurposeCategory.class, EditTableContextAction.class);
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