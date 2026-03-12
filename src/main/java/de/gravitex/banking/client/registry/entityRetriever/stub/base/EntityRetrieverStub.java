package de.gravitex.banking.client.registry.entityRetriever.stub.base;

import java.util.List;

import de.gravitex.banking.client.accessor.IBankingAccessor;

public abstract class EntityRetrieverStub<T> {

	public abstract List<T> fetchEntities(IBankingAccessor aBankingAccessor);
}