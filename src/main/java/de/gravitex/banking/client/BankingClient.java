package de.gravitex.banking.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.tabbedpanel.BookingSummaryTabbedPanel;
import de.gravitex.banking.client.gui.tabbedpanel.BookingTabbedPanel;
import de.gravitex.banking.client.gui.tabbedpanel.PartnerTabbedPanel;
import de.gravitex.banking.client.gui.tabbedpanel.StandingOrderPanelTabbedPanel;
import de.gravitex.banking.client.gui.tabbedpanel.base.TabbedPanel;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.CreditInstitute;
import de.gravitex.banking_core.entity.base.IdEntity;
import de.gravitex.banking_core.entity.view.BookingView;

public class BankingClient extends JFrame implements EntityTablePanelListener, ChangeListener {

	private static final long serialVersionUID = -8912127709159268030L;

	private EntityTablePanel creditInstituteTable;

	private EntityTablePanel accountTable;

	private EntityTablePanel bookingTable;

	private JTabbedPane mainPane;

	private Object selectedEntity;

	public BankingClient() {

		super();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());

		setTitle("Banking (DB:"+ApplicationRegistry.getInstance().getAdminData().getDatasourceName()+")");
		setSize(900, 600);
		setLocation(200, 200);

		makeLayout();

		fill();
	}

	private void makeLayout() {

		creditInstituteTable = new EntityTablePanel("Institute", this, true);
		accountTable = new EntityTablePanel("Konten", this, true);
		bookingTable = new EntityTablePanel("Buchungen", this, true);

		mainPane = new JTabbedPane();
		mainPane.addChangeListener(this);
		add(mainPane, BorderLayout.CENTER);

		mainPane.addTab("Buchungen", getBookingPanel());
		mainPane.addTab("Partner", getPartnerPanel());
		mainPane.addTab("Übersicht", getBookingSummaryPanel());
		mainPane.addTab("Dauerauftrag", getStandingOrderPanel());
	}

	private TabbedPanel getStandingOrderPanel() {
		TabbedPanel panel = new StandingOrderPanelTabbedPanel();
		return panel;
	}

	private TabbedPanel getBookingSummaryPanel() {
		TabbedPanel panel = new BookingSummaryTabbedPanel();
		return panel;
	}

	private TabbedPanel getPartnerPanel() {
		return new PartnerTabbedPanel();
	}

	private TabbedPanel getBookingPanel() {
		TabbedPanel panel = new BookingTabbedPanel();
		panel.setLayout(new GridLayout(3, 1));
		panel.add(creditInstituteTable);
		panel.add(accountTable);
		panel.add(bookingTable);
		return panel;
	}

	private void fill() {
		creditInstituteTable.displayEntities(ApplicationRegistry.getInstance().getBankingAccessor().readCreditInstitutes());
	}

	public void onEntitySelected(Object aEntity) {

		selectedEntity = aEntity;
		
		System.out.println("onEntitySelected --> " + selectedEntity + " [" + aEntity.getClass().getSimpleName() + "]");
		if (aEntity instanceof CreditInstitute) {
			List<Account> creditInstitutes = ApplicationRegistry.getInstance().getBankingAccessor()
					.readAccounts((CreditInstitute) aEntity);
			accountTable.displayEntities(creditInstitutes);
		}
		if (aEntity instanceof Account) {
			List<BookingView> accounts = ApplicationRegistry.getInstance().getBankingAccessor()
					.readBookingViewsByAccount((Account) aEntity);
			bookingTable.displayEntities(accounts);
		}
	}

	public void stateChanged(ChangeEvent e) {
		((TabbedPanel) mainPane.getComponentAt(mainPane.getSelectedIndex())).onPanelActivated(selectedEntity);
	}

	public void onEntityDoubeClicked(Object aEntity) {
		// TODO Auto-generated method stub
	}

	public BankingClient onStartUp() {
		/*
		BookingAdminData adminData = ApplicationRegistry.getInstance().getAdminData();
		String database = adminData.getDatasourceName();
		String importDir = adminData.getImportRoot();
		ApplicationRegistry.getInstance().getInteractionHandler().showMessage("Datenbank : " + database + " (Import von:"+importDir+")", this);
		*/
		return this;
	}

	@Override
	public Object getSelectedObject() {
		return selectedEntity;
	}

	@Override
	public void acceptEditedEntity(IdEntity aEntity) {
		if (aEntity instanceof CreditInstitute) {
			ApplicationRegistry.getInstance().getBankingAccessor().saveCreditInstitute((CreditInstitute) aEntity);
		}		
	}
}