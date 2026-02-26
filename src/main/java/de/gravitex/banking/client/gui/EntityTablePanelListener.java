package de.gravitex.banking.client.gui;

import de.gravitex.banking_core.entity.base.IdEntity;

public interface EntityTablePanelListener {

	void onEntitySelected(Object aEntity);

	void onEntityDoubeClicked(Object aEntity);

	Object getSelectedObject();

	void acceptEditedEntity(IdEntity aEntity);
}