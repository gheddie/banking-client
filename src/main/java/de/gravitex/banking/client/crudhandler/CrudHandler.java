package de.gravitex.banking.client.crudhandler;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.exception.CrudException;
import de.gravitex.banking_core.entity.base.IdEntity;

public interface CrudHandler {

	void evaluateResult(HttpPatchResult aHttpPatchResult) throws CrudException;

	void handleException(CrudException aCrudException);

	void onSuccessFullyPatched(IdEntity aEntity);
}