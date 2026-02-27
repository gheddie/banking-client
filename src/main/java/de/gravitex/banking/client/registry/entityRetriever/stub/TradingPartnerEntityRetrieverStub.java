package de.gravitex.banking.client.registry.entityRetriever.stub;

import java.util.List;

import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.registry.entityRetriever.stub.base.EntityRetrieverStub;
import de.gravitex.banking_core.entity.TradingPartner;

public class TradingPartnerEntityRetrieverStub extends EntityRetrieverStub<TradingPartner> {

	@Override
	public List<TradingPartner> fetchEntities(IBankingAccessor aBankingAccessor) {
		return aBankingAccessor.readTradingPartners();
	}
}