package de.gravitex.banking.client.registry.entityRetriever.stub;

import java.util.List;

import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.registry.entityRetriever.stub.base.EntityRetrieverStub;
import de.gravitex.banking_core.entity.Account;

public class AccountEntityRetrieverStub extends EntityRetrieverStub<Account> {

	@Override
	public List<Account> fetchEntities(IBankingAccessor aBankingAccessor) {
		return aBankingAccessor.readAccounts();
	}
}