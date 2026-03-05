package de.gravitex.banking.client.crudhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.exception.CrudException;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.gui.dialog.editor.EntityCrudDialog;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.base.IdEntity;
import de.gravitex.banking_core.entity.base.NoIdEntity;

public class DefaultCrudHandler implements CrudHandler {
	
	private Logger logger = LoggerFactory.getLogger(DefaultCrudHandler.class);

	public void editEntity(IdEntity aEntity, ActionProvider actionProvider) {
		new EntityCrudDialog(aEntity, actionProvider, this, true).setVisible(true);		
	}
	
	@Override
	public void createEntity(Class<? extends NoIdEntity> aEntityClass, ActionProvider actionProvider) {
		new EntityCrudDialog(makeEntity(aEntityClass), actionProvider, this, false).setVisible(true);
	}

	private IdEntity makeEntity(Class<? extends NoIdEntity> aEntityClass) {
		try {
			return (IdEntity) aEntityClass.getConstructor(new Class[] {}).newInstance(new Object[] {});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void evaluatePatchResult(HttpPatchResult aHttpPatchResult) throws CrudException {		
		if (aHttpPatchResult == null) {
			throw new CrudException("no patch result was provided!!!", null);
		}
		if (!aHttpPatchResult.hasValidStatusCode()) {
			throw new CrudException(aHttpPatchResult.getErrorMessage(), null);
		}	
	}
	
	@Override
	public void evaluatePutResult(HttpPutResult aHttpPutResult) throws CrudException {
		if (aHttpPutResult == null) {
			throw new CrudException("no put result was provided!!!", null);
		}
		if (!aHttpPutResult.hasValidStatusCode()) {
			throw new CrudException(aHttpPutResult.getErrorMessage(), null);
		}
	}

	@Override
	public void handleException(CrudException aCrudException) {
		ApplicationRegistry.getInstance().getInteractionHandler().showError(aCrudException.getMessage(),
				ApplicationRegistry.getInstance().getParentView());
	}

	@Override
	public void onSuccessFullyPatched(IdEntity aEntity) {
		logger.info("...onSuccessFullyPatched ["+aEntity.getClass().getCanonicalName()+"]...");		
	}

	@Override
	public void onSuccessFullyPut(IdEntity entity) {
		// TODO Auto-generated method stub		
	}
}