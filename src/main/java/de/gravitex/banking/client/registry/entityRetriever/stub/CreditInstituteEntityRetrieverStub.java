package de.gravitex.banking.client.registry.entityRetriever.stub;

import java.util.List;

import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.registry.entityRetriever.stub.base.EntityRetrieverStub;
import de.gravitex.banking_core.entity.CreditInstitute;

public class CreditInstituteEntityRetrieverStub extends EntityRetrieverStub<CreditInstitute> {

	@SuppressWarnings("unchecked")
	@Override
	public List<CreditInstitute> fetchEntities(IBankingAccessor aBankingAccessor) {
		return (List<CreditInstitute>) aBankingAccessor.readCreditInstitutes(this).getEntityList();
	}
}