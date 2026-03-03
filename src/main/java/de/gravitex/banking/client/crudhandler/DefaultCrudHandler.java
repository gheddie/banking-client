package de.gravitex.banking.client.crudhandler;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.exception.CrudException;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.gui.dialog.editor.EntityEditorDialog;
import de.gravitex.banking_core.entity.base.IdEntity;

public class DefaultCrudHandler implements CrudHandler {

	public void editEntity(IdEntity aEntity, ActionProvider actionProvider) {
		new EntityEditorDialog(aEntity, actionProvider, this).setVisible(true);		
	}

	@Override
	public void evaluateResult(HttpPatchResult aHttpPatchResult) throws CrudException {		
		if (aHttpPatchResult == null) {
			throw new CrudException("no patch result was provided!!!", null);
		}
		if (!aHttpPatchResult.hasValidStatusCode()) {
			throw new CrudException("received invalid status code [" + aHttpPatchResult.getStatusCode() + "]!!!", null);
		}	
	}

	@Override
	public void handleException(CrudException aCrudException) {
		// TODO
		aCrudException.printStackTrace();
	}

	@Override
	public void onSuccessFullyPatched(IdEntity aEntity) {
		System.out.println("...onSuccessFullyPatched ["+aEntity.getClass().getCanonicalName()+"]...");		
	}
}