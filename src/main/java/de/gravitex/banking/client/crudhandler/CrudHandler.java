package de.gravitex.banking.client.crudhandler;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.exception.CrudException;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking.entity.base.NoIdEntity;

public interface CrudHandler {

	void evaluatePatchResult(HttpPatchResult aHttpPatchResult, ActionProvider actionProvider) throws CrudException;

	void handleException(CrudException aCrudException);

	void onSuccessFullyPatched(IdEntity aEntity);

	void editEntity(IdEntity aEntity, ActionProvider actionProvider);

	void createEntity(Class<? extends NoIdEntity> aEntityClass, ActionProvider actionProvider);

	void evaluatePutResult(HttpPutResult aHttpPutResult, ActionProvider actionProvider) throws CrudException;

	void onSuccessFullyPut(IdEntity entity);
}