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
import de.gravitex.banking.entity.PurposeCategory;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking.entity.base.NoIdEntity;

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

	@SuppressWarnings("unchecked")
	private void fillData() {
		List<PurposeCategory> purposeCategories = (List<PurposeCategory>) ApplicationRegistry.getInstance()
				.getBankingAccessor().readPurposeCategorys().getEntityList();
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
	public void onEntityDoubleClicked(Object aEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getSelectedObject() {
		return selectedPurposeCategory;
	}

	@Override
	public HttpPatchResult acceptEditedEntity(IdEntity aEntity) {
		return ApplicationRegistry.getInstance().getBankingAccessor().patchPurposeCategory(selectedPurposeCategory);
	}

	@Override
	public HttpPutResult acceptCreatedEntity(IdEntity entity) {
		return ApplicationRegistry.getInstance().getBankingAccessor().putPurposeCategory((PurposeCategory) entity);
	}

	@Override
	public ActionFilter getActionFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<? extends NoIdEntity> reloadEntities(Class<?> aEntityClass) {
		return (List<? extends NoIdEntity>) ApplicationRegistry.getInstance().getBankingAccessor()
				.readPurposeCategorys().getEntityList();
	}
}