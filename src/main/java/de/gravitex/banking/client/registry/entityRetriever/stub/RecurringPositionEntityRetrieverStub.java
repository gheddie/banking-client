package de.gravitex.banking.client.registry.entityRetriever.stub;

import java.util.List;

import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.registry.entityRetriever.stub.base.EntityRetrieverStub;
import de.gravitex.banking.entity.RecurringPosition;

public class RecurringPositionEntityRetrieverStub extends EntityRetrieverStub<RecurringPosition> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RecurringPosition> fetchEntities(IBankingAccessor aBankingAccessor) {
		return (List<RecurringPosition>) aBankingAccessor.readEntityList(RecurringPosition.class).getEntityList();
	}
}