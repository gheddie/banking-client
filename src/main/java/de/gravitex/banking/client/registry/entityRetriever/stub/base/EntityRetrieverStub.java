package de.gravitex.banking.client.registry.entityRetriever.stub.base;

import java.util.List;

import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.accessor.util.EntityRequester;
import de.gravitex.banking.client.exception.EntityRequestException;

public abstract class EntityRetrieverStub<T> implements EntityRequester {

	public abstract List<T> fetchEntities(IBankingAccessor aBankingAccessor);
	
	@Override
	public void handleRequestException(EntityRequestException aEntityRequestException) {
		// TODO Auto-generated method stub	
	}
}