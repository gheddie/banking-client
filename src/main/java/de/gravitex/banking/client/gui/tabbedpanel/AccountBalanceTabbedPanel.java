package de.gravitex.banking.client.gui.tabbedpanel;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking.client.gui.tabbedpanel.base.TabbedPanel;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking.entity.base.NoIdEntity;
import de.gravitex.banking_core.dto.AccountInfo;

public class AccountBalanceTabbedPanel extends TabbedPanel implements EntityTablePanelListener {

	private static final long serialVersionUID = 4624017183053381728L;
	
	private EntityTablePanel accountInfoTable;

	@Override
	protected void putListeners() {
		// TODO Auto-generated method stub
	}

	@Override
	protected LayoutManager getPanelLayout() {
		return new BorderLayout();
	}

	@Override
	protected void init() {
		accountInfoTable = new EntityTablePanel("Konto-Stände", this, true, AccountInfo.class);
		add(accountInfoTable, BorderLayout.CENTER);
	}

	@Override
	public void onPanelActivated(Object aContextEntity) {
		fillData();
	}

	private void fillData() {
		HttpGetResult result = ApplicationRegistry.getInstance().getBankingAccessor().readAccountInfos(null);		
		accountInfoTable.displayEntities(result.getEntityList());
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onEntitySelected(Object aEntity) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onEntityDoubeClicked(Object aEntity) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object getSelectedObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpPatchResult acceptEditedEntity(IdEntity aEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpPutResult acceptCreatedEntity(IdEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFilter getActionFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends NoIdEntity> reloadEntities(Class<?> aEntityClass) {
		// TODO Auto-generated method stub
		return null;
	}
}