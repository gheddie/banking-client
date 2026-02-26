package de.gravitex.banking.client.gui;

import java.util.List;

import de.gravitex.banking.client.gui.action.base.TableContextAction;

public interface EntityTableListener {

	void handleDoubeClick(int aSelectedRow);

	void acceptEntities(List<?> aSortedEntities);

	List<TableContextAction<?>> getContextActions();
}