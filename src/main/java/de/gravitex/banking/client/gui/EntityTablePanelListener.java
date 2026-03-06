package de.gravitex.banking.client.gui;

import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking_core.entity.base.IdEntity;
import de.gravitex.banking_core.entity.base.NoIdEntity;

public interface EntityTablePanelListener {

	void onEntitySelected(Object aEntity);

	void onEntityDoubeClicked(Object aEntity);

	Object getSelectedObject();

	HttpPatchResult acceptEditedEntity(IdEntity aEntity);
	
	HttpPutResult acceptCreatedEntity(IdEntity entity);

	ActionFilter getActionFilter();

	List<? extends NoIdEntity> reloadEntities(Class<?> aEntityClass);
}