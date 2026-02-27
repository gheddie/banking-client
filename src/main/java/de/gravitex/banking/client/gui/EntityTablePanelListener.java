package de.gravitex.banking.client.gui;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking_core.entity.base.IdEntity;

public interface EntityTablePanelListener {

	void onEntitySelected(Object aEntity);

	void onEntityDoubeClicked(Object aEntity);

	Object getSelectedObject();

	HttpPatchResult acceptEditedEntity(IdEntity aEntity);
}