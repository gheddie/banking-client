package de.gravitex.banking.client.gui.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.exception.ActionException;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.base.IdEntity;

public class EditTableContextAction extends TableContextAction<IdEntity> {
	
	private Logger logger = LoggerFactory.getLogger(EditTableContextAction.class);

	public EditTableContextAction(ActionProvider actionProvider) {
		super("Bearbeiten", actionProvider);
	}

	@Override
	protected void executeAction(IdEntity aEntity) {
		logger.info("Bearbeiten von [" + aEntity + "]");
		ApplicationRegistry.getInstance().getCrudHandler().editEntity(aEntity, getActionProvider());
	}

	@Override
	protected void checkContextObject(Object aContextObject) throws ActionException {
		if (!(aContextObject instanceof IdEntity)) {
			throw new ActionException("Objekt vom Typ [" + aContextObject.getClass().getSimpleName()
					+ "] kann nicht bearbeitet werden!!!", null);
		}
	}
}