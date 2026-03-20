package de.gravitex.banking.client.accessor;

import java.time.LocalDate;
import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpDeleteResult;
import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPostResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking.entity.base.NoIdEntity;
import de.gravitex.banking_core.dto.MergeTradingPartners;

public interface IBankingAccessor {
	
	// generic crud
	HttpGetResult findAllEntities(Class<? extends IdEntity> aEntityClass);
	HttpDeleteResult deleteEntity(IdEntity aEntity);
	HttpPutResult putEntity(IdEntity aEntity);
	HttpPatchResult patchEntity(IdEntity aEntity);
	HttpGetResult readEntityList(Class<?> aEntityClass);
	HttpGetResult readEntity(Class<?> aEntityClass);
	HttpGetResult readEntityById(Long aEntityId, Class<?> aEntityClass);
	public HttpGetResult readEntityListByReference(Class<? extends NoIdEntity> aResultClass, IdEntity aReference, String aReferringAttribute);
	
	// booking import
	HttpGetResult importBookings(Account account);
	HttpGetResult readUnprocessedBookingImports(Account account);
	HttpGetResult importBookingFile(Account account, String aBookingFileName);
	
	// booking overview
	HttpPostResult createBookingOverview(Account account, LocalDate from, LocalDate to);

	// misc	
	HttpPostResult mergeTradingPartners(MergeTradingPartners aMergeTradingPartners);
	HttpPostResult createBookingProgress(LocalDate from, LocalDate to, List<TradingPartner> aTradingPartners);	
}