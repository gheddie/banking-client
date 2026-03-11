package de.gravitex.banking.client.registry.entityRetriever.stub;

import java.util.List;

import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.registry.entityRetriever.stub.base.EntityRetrieverStub;
import de.gravitex.banking.entity.PurposeCategory;

public class PurposeCategoryEntityRetrieverStub extends EntityRetrieverStub<PurposeCategory> {

	@SuppressWarnings("unchecked")
	@Override
	public List<PurposeCategory> fetchEntities(IBankingAccessor aBankingAccessor) {
		return (List<PurposeCategory>) aBankingAccessor.readPurposeCategorys(null).getEntityList();
	}
}