package de.gravitex.banking.client.registry.entityRetriever.stub;

import java.util.List;

import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.registry.entityRetriever.stub.base.EntityRetrieverStub;
import de.gravitex.banking.entity.Account;

public class AccountEntityRetrieverStub extends EntityRetrieverStub<Account> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> fetchEntities(IBankingAccessor aBankingAccessor) {
		return (List<Account>) aBankingAccessor.readEntityList(Account.class).getEntityList();
	}
}