package de.gravitex.banking.client.gui.tabbedpanel;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.util.List;

import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.tabbedpanel.base.TabbedPanel;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.dto.AccountInfo;

public class AccountBalanceTabbedPanel extends TabbedPanel {

	private static final long serialVersionUID = 4624017183053381728L;
	
	private EntityTablePanel accountInfoTable;

	@Override
	protected void putListeners() {
		// TODO Auto-generated method stub
	}

	@Override
	protected LayoutManager getPanelLayout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void init() {
		// accountInfoTable = new EntityTablePanel("Konto-Stände", this, true, AccountInfo.class);
		// add(accountInfoTable, BorderLayout.CENTER);
	}

	@Override
	public void onPanelActivated(Object aContextEntity) {
		fillData();
	}

	private void fillData() {
		List<AccountInfo> accountInfos = ApplicationRegistry.getInstance().getBankingAccessor().readAccountInfos(null);		
		accountInfoTable.displayEntities(accountInfos);
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
	}
}