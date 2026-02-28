package de.gravitex.banking.client.gui.action;

import de.gravitex.banking.client.accessor.response.HttpDeleteResult;
import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.base.IdEntity;

public class DeleteTableContextAction extends TableContextAction<IdEntity> {

	public DeleteTableContextAction(ActionProvider tActionProvider) {
		super("Löschen", tActionProvider);
	}

	@Override
	protected void checkContextObject(Object aContextObject) throws ActionException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void executeAction(IdEntity contextObject) {		
		if (ApplicationRegistry.getInstance().getInteractionHandler()
				.yesNo("Entität [" + contextObject.getClass().getSimpleName() + "] löschen?", false, null)) {
			HttpDeleteResult deleteResult = ApplicationRegistry.getInstance().getBankingAccessor()
					.deleteEntity(contextObject);
			if (!deleteResult.hasValidStatusCode()) {
				ApplicationRegistry.getInstance().getInteractionHandler().showError(deleteResult.getErrorMessage(),
						null);
			}
		}		
	}
}