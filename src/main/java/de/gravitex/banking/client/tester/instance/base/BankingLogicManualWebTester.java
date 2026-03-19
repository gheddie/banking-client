package de.gravitex.banking.client.tester.instance.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.tester.exception.ManualWebTesterException;
import de.gravitex.banking.client.tester.matcher.ResponseLengthValidator;
import de.gravitex.banking.client.tester.matcher.exception.base.ExceptionMatcher;
import de.gravitex.banking.client.tester.util.WebTestWatcher;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.RecurringPosition;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.entity.TradingPartnerBookingHistory;
import de.gravitex.banking.enumerated.RecurringInterval;
import de.gravitex.banking_core.dto.BookingImportSummary;
import de.gravitex.banking_core.dto.UnprocessedBookingImport;

public abstract class BankingLogicManualWebTester extends ManualWebTester {

	private static final String UNPROCESSED_BOOKING_IMPORTS = "UNPROCESSED_BOOKING_IMPORTS";
	
	public BankingLogicManualWebTester(WebTestWatcher aWebTestWatcher, boolean isActive) {
		super(aWebTestWatcher, isActive);
	}

	@SuppressWarnings("unchecked")
	protected void importBookings(Account account, String aBookingFileName, int aExpectedImportCount, int aExpectedUnprocessedCount) {			
		
		expectSuccess(getBankingAccessor().readUnprocessedBookingImports(account), UNPROCESSED_BOOKING_IMPORTS,
				ResponseLengthValidator.forExpectedResponseSize(aExpectedUnprocessedCount));
		List<UnprocessedBookingImport> unprocessedBookingImports = (List<UnprocessedBookingImport>) getObjectCache()
				.getObject(UNPROCESSED_BOOKING_IMPORTS);
		Set<String> unprocessedFileNames = new HashSet<>();
		for (UnprocessedBookingImport aUnprocessedBookingImport : unprocessedBookingImports) {
			unprocessedFileNames.add(aUnprocessedBookingImport.getBookingFileName());
		}
		if (!unprocessedFileNames.contains(aBookingFileName)) {
			throw new ManualWebTesterException("unporcessed booking file {" + aBookingFileName
					+ "} not present for account {" + account.getName() + "}!!!");
		}
		HttpGetResult importBookingFile = getBankingAccessor().importBookingFile(account, aBookingFileName);		
		BookingImportSummary summary = (BookingImportSummary) importBookingFile.getEntity();
		getValidator().validate(aExpectedImportCount, summary.getImportedBookings().size(), "expected import count");
		expectSuccess(importBookingFile, null, null);
	}
	
	@SuppressWarnings("unchecked")
	protected TradingPartner getTradingPartner(String aTradingKey) {
		List<TradingPartner> aTradingPartners = (List<TradingPartner>) getBankingAccessor().readTradingPartners().getEntityList();
		for (TradingPartner aTradingPartner : aTradingPartners) {
			if (aTradingPartner.getTradingKey().equals(aTradingKey)) {
				return aTradingPartner;		
			}
		}
		throw new ManualWebTesterException("trading partner not found for trading key {" + aTradingKey + "}!!!");
	}
	
	@SuppressWarnings("unchecked")
	protected void attachRecurringPosition(String aTradingKey, RecurringInterval aRecurringInterval, boolean aIncoming, boolean aExpectSuccess, ExceptionMatcher aExceptionMatcher) {

		List<RecurringPosition> recurringPositions = (List<RecurringPosition>) getBankingAccessor()
				.readRecurringPositions().getEntityList();
		Map<String, RecurringPosition> recurringPositionMap = new HashMap<>();
		for (RecurringPosition aRecurringPosition : recurringPositions) {
			recurringPositionMap.put(makeRecurringPositionKey(aRecurringPosition), aRecurringPosition);
		}
		TradingPartner aTradingPartner = getTradingPartner(aTradingKey);
		String rpKey = makeRecurringPositionKey(aRecurringInterval, aIncoming);
		RecurringPosition rpos = recurringPositionMap.get(rpKey);
		if (rpos == null) {
			throw new ManualWebTesterException("unable to find recurring position by key {"+rpKey+"}!!!");
		}
		aTradingPartner.setRecurringPosition(rpos);
		if (aExpectSuccess) {
			expectSuccess(getBankingAccessor().patchTradingPartner(aTradingPartner), null, null);	
		} else {
			expectFailure(getBankingAccessor().patchTradingPartner(aTradingPartner), aExceptionMatcher);
		}
	}

	private String makeRecurringPositionKey(RecurringPosition aRecurringPosition) {
		return makeRecurringPositionKey(aRecurringPosition.getRecurringInterval(), aRecurringPosition.getIncoming());
	}

	private String makeRecurringPositionKey(RecurringInterval aRecurringInterval, boolean aIncoming) {
		String tmp = aRecurringInterval.name();
		if (aIncoming) {
			return tmp + "@1";	
		}
		return tmp + "@0";
	}
	
	protected List<TradingPartner> getTradingPartners(String... aTradingPartnerKeys) {
		List<TradingPartner> list = new ArrayList<>();
		for (String aTradingPartnerKey : aTradingPartnerKeys) {
			list.add(getTradingPartner(aTradingPartnerKey));
		}
		return list;
	}
	
	protected Map<String, List<TradingPartnerBookingHistory>> mapTradingPartnerBookingHistoriesByTradingKey(
			List<TradingPartnerBookingHistory> aHistories) {
		Map<String, List<TradingPartnerBookingHistory>> map = new HashMap<>();
		for (TradingPartnerBookingHistory aHistory : aHistories) {
			if (map.get(aHistory.getTradingPartner().getTradingKey()) == null) {
				map.put(aHistory.getTradingPartner().getTradingKey(), new ArrayList<>());
			}
			map.get(aHistory.getTradingPartner().getTradingKey()).add(aHistory);
		}
		return map;
	}
}