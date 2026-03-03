package de.gravitex.banking.client.gui;

import java.util.List;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking_core.entity.base.NoIdEntity;

public interface EntityTableListener {

	void handleDoubeClick(int aSelectedRow);

	void acceptEntities(List<?> aSortedEntities);

	List<TableContextAction<?>> getContextActions();

	Class<? extends NoIdEntity> getEntityClass();

	EntityTablePanelListener getPanelListener();
}