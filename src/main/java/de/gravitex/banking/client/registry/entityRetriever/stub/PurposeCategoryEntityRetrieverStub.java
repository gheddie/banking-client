package de.gravitex.banking.client.registry.entityRetriever.stub;

import java.util.List;

import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.registry.entityRetriever.stub.base.EntityRetrieverStub;
import de.gravitex.banking_core.entity.PurposeCategory;

public class PurposeCategoryEntityRetrieverStub extends EntityRetrieverStub<PurposeCategory> {

	@Override
	public List<PurposeCategory> fetchEntities(IBankingAccessor aBankingAccessor) {
		return aBankingAccessor.readPurposeCategorys();
	}
}