package de.gravitex.banking.client.gui.tabbedpanel;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking.client.gui.tabbedpanel.base.TabbedPanel;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.PurposeCategory;
import de.gravitex.banking_core.entity.TradingPartner;
import de.gravitex.banking_core.entity.base.IdEntity;
import de.gravitex.banking_core.entity.base.NoIdEntity;

public class PurposeCategoryTabbedPanel extends TabbedPanel implements EntityTablePanelListener {

	private static final long serialVersionUID = 1351061739481570396L;
	
	private EntityTablePanel purposeCategoryTable;

	private PurposeCategory selectedPurposeCategory;

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
		purposeCategoryTable = new EntityTablePanel("Kategorien", this, true,
				PurposeCategory.class);
		add(purposeCategoryTable, BorderLayout.CENTER);
	}

	@Override
	public void onPanelActivated(Object aContextEntity) {
		fillData();
	}

	private void fillData() {
		List<PurposeCategory> purposeCategories = ApplicationRegistry.getInstance().getBankingAccessor()
				.readPurposeCategorys();
		purposeCategoryTable.displayEntities(purposeCategories);		
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onEntitySelected(Object aEntity) {
		selectedPurposeCategory = (PurposeCategory) aEntity;		
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
		ApplicationRegistry.getInstance().getBankingAccessor().createPurposeCategory((PurposeCategory) entity);
		return null;
	}

	@Override
	public ActionFilter getActionFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends NoIdEntity> reloadEntities(Class<? extends NoIdEntity> aEntityClass) {
		return ApplicationRegistry.getInstance().getBankingAccessor().readPurposeCategorys();
	}
}