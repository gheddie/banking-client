package de.gravitex.banking.client.registry.entityRetriever;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.exception.BankingException;
import de.gravitex.banking.client.registry.entityRetriever.stub.AccountEntityRetrieverStub;
import de.gravitex.banking.client.registry.entityRetriever.stub.PurposeCategoryEntityRetrieverStub;
import de.gravitex.banking.client.registry.entityRetriever.stub.TradingPartnerEntityRetrieverStub;
import de.gravitex.banking.client.registry.entityRetriever.stub.base.EntityRetrieverStub;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.PurposeCategory;
import de.gravitex.banking_core.entity.TradingPartner;
import de.gravitex.banking_core.entity.base.IdEntity;

public class AllEntityRetriever {

	private static final Map<Class<? extends IdEntity>, EntityRetrieverStub<?>> RETRIEVERS = new HashMap<>();
	static {
		RETRIEVERS.put(PurposeCategory.class, new PurposeCategoryEntityRetrieverStub());
		RETRIEVERS.put(TradingPartner.class, new TradingPartnerEntityRetrieverStub());
		RETRIEVERS.put(Account.class, new AccountEntityRetrieverStub());
	}

	public List<?> retrieveEntities(Class<?> aEntityClass, IBankingAccessor aBankingAccessor) throws BankingException {
		EntityRetrieverStub<?> stub = RETRIEVERS.get(aEntityClass);
		if (stub == null) {
			throw new BankingException(
					"no retriever stub found for entity class [" + aEntityClass.getCanonicalName() + "]!!!", null);
		}
		return stub.fetchEntities(aBankingAccessor);
	}
}