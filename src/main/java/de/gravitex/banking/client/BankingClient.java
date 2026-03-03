package de.gravitex.banking.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking.client.gui.tabbedpanel.BookingTabbedPanel;
import de.gravitex.banking.client.gui.tabbedpanel.PartnerTabbedPanel;
import de.gravitex.banking.client.gui.tabbedpanel.base.TabbedPanel;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.Booking;
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

		creditInstituteTable = new EntityTablePanel("Institute", this, true, CreditInstitute.class);
		accountTable = new EntityTablePanel("Konten", this, true, Account.class);
		bookingTable = new EntityTablePanel("Buchungen", this, true, BookingView.class);

		mainPane = new JTabbedPane();
		mainPane.addChangeListener(this);
		add(mainPane, BorderLayout.CENTER);
		
		initTabbedPanels();
	}

	private void initTabbedPanels() {
		mainPane.addTab("Buchungen", getBookingPanel());
		mainPane.addTab("Partner", getPartnerPanel());
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
		TabbedPanel tabbedPanel = (TabbedPanel) mainPane.getComponentAt(mainPane.getSelectedIndex());
		tabbedPanel.onPanelActivated(selectedEntity);
	}

	public void onEntityDoubeClicked(Object aEntity) {

	}

	public BankingClient onStartUp() {
		return this;
	}

	@Override
	public Object getSelectedObject() {
		return selectedEntity;
	}

	@Override
	public HttpPatchResult acceptEditedEntity(IdEntity aEntity) {
		if (aEntity instanceof CreditInstitute) {
			return ApplicationRegistry.getInstance().getBankingAccessor()
					.saveCreditInstitute((CreditInstitute) aEntity);
		}
		if (aEntity instanceof Account) {
			return ApplicationRegistry.getInstance().getBankingAccessor().saveAccount((Account) aEntity);
		}
		if (aEntity instanceof Booking) {
			return ApplicationRegistry.getInstance().getBankingAccessor().saveBooking((Booking) aEntity);
		}
		return null;		
	}

	@Override
	public ActionFilter getActionFilter() {
		// TODO Auto-generated method stub
		return null;
	}
}