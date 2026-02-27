package de.gravitex.banking.client.gui.action;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.gui.dialog.editor.EntityEditorDialog;
import de.gravitex.banking_core.entity.base.IdEntity;

public class EditTableContextAction extends TableContextAction<IdEntity> {

	public EditTableContextAction(ActionProvider actionProvider) {
		super("Bearbeiten", actionProvider);
	}

	@Override
	protected void executeAction(IdEntity aEntity) {
		System.out.println("Bearbeiten von [" + aEntity + "]");
		new EntityEditorDialog(aEntity, getActionProvider()).setVisible(true);
	}

	@Override
	protected void checkContextObject(Object aContextObject) throws ActionException {
		if (!(aContextObject instanceof IdEntity)) {
			throw new ActionException("Objekt vom Typ [" + aContextObject.getClass().getSimpleName()
					+ "] kann nicht bearbeitet werden!!!", null);
		}
	}
}