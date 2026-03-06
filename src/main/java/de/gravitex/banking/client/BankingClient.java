package de.gravitex.banking.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.accessor.util.EntityRequester;
import de.gravitex.banking.client.exception.EntityRequestException;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking.client.gui.tabbedpanel.AccountBalanceTabbedPanel;
import de.gravitex.banking.client.gui.tabbedpanel.BookingTabbedPanel;
import de.gravitex.banking.client.gui.tabbedpanel.PartnerTabbedPanel;
import de.gravitex.banking.client.gui.tabbedpanel.PurposeCategoryTabbedPanel;
import de.gravitex.banking.client.gui.tabbedpanel.base.TabbedPanel;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.Booking;
import de.gravitex.banking_core.entity.CreditInstitute;
import de.gravitex.banking_core.entity.base.IdEntity;
import de.gravitex.banking_core.entity.base.NoIdEntity;
import de.gravitex.banking_core.entity.view.BookingView;

public class BankingClient extends JFrame implements EntityTablePanelListener, ChangeListener, EntityRequester {
	
	private Logger logger = LoggerFactory.getLogger(BankingClient.class);

	private static final long serialVersionUID = -8912127709159268030L;

	private EntityTablePanel creditInstituteTable;

	private EntityTablePanel accountTable;

	private EntityTablePanel bookingTable;

	private JTabbedPane mainPane;

	private Object selectedEntity;

	private CreditInstitute selectedCreditInstitute;

	private Account selectedAccount;

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
		mainPane.addTab("Partner", new PartnerTabbedPanel());
		mainPane.addTab("Kategorien", new PurposeCategoryTabbedPanel());
		mainPane.addTab("Kontostände", new AccountBalanceTabbedPanel());
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
		List<CreditInstitute> creditInstitutes = ApplicationRegistry.getInstance().getBankingAccessor()
				.readCreditInstitutes(this);
		if (creditInstitutes != null) {
			creditInstituteTable.displayEntities(creditInstitutes);	
		}		
	}

	public void onEntitySelected(Object aEntity) {

		selectedEntity = aEntity;
		
		logger.info("onEntitySelected --> " + selectedEntity + " [" + aEntity.getClass().getSimpleName() + "]");
		if (aEntity instanceof CreditInstitute) {
			selectedCreditInstitute = (CreditInstitute) aEntity;
			List<Account> creditInstitutes = ApplicationRegistry.getInstance().getBankingAccessor()
					.readAccounts((CreditInstitute) aEntity, null);
			accountTable.displayEntities(creditInstitutes);
		}
		if (aEntity instanceof Account) {
			selectedAccount = (Account) aEntity;
			List<BookingView> accounts = ApplicationRegistry.getInstance().getBankingAccessor()
					.readBookingViewsByAccount((Account) aEntity, null);
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
					.patchCreditInstitute((CreditInstitute) aEntity);
		}
		if (aEntity instanceof Account) {
			return ApplicationRegistry.getInstance().getBankingAccessor().patchAccount((Account) aEntity);
		}
		if (aEntity instanceof Booking) {
			return ApplicationRegistry.getInstance().getBankingAccessor().patchBooking((Booking) aEntity);
		}
		return null;		
	}

	@Override
	public ActionFilter getActionFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends NoIdEntity> reloadEntities(Class<?> aEntityClass) {
		if (aEntityClass.equals(CreditInstitute.class)) {
			return ApplicationRegistry.getInstance().getBankingAccessor().readCreditInstitutes(null);
		}
		if (aEntityClass.equals(Account.class)) {
			return ApplicationRegistry.getInstance().getBankingAccessor().readAccounts(selectedCreditInstitute, null);
		}
		if (aEntityClass.equals(BookingView.class)) {
			return ApplicationRegistry.getInstance().getBankingAccessor().readBookingViewsByAccount(selectedAccount, null);
		}		
		return null;
	}

	@Override
	public HttpPutResult acceptCreatedEntity(IdEntity entity) {
		if (entity instanceof CreditInstitute) {
			return ApplicationRegistry.getInstance().getBankingAccessor().putCreditInstitute((CreditInstitute) entity);	
		}		
		if (entity instanceof Account) {
			return ApplicationRegistry.getInstance().getBankingAccessor().putAccount((Account) entity);	
		}		
		return null;
	}

	@Override
	public void handleRequestException(EntityRequestException aEntityRequestException) {
		ApplicationRegistry.getInstance().getInteractionHandler().showError(aEntityRequestException.buildMessage(), this);
	}
}