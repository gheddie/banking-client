package de.gravitex.banking.client.gui.logic.bookingoverview;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.view.BookingView;
import de.gravitex.banking_core.formatter.DateTimeValueFormatter;
import de.gravitex.banking_core.util.StringHelper;

public abstract class BookingOverviewModel {

	private BookingView referenceBooking;

	private Account account;

	private Map<String, List<BookingView>> mappedByEffectivePurposeKey = new HashMap<>();

	private List<BookingOverviewEntry> generatedEntries;
	
	protected DateTimeValueFormatter dateFormatter = new DateTimeValueFormatter();

	private List<BookingView> filteredBookings;

	public BookingOverviewModel(BookingView aReferenceBooking) {
		super();
		this.referenceBooking = aReferenceBooking;
		readEntities();
	}

	private void readEntities() {
		IBankingAccessor accessor = ApplicationRegistry.getInstance().getBankingAccessor();
		account = (Account) accessor.readAccountById(referenceBooking.getAccountId(), null).getEntity();
	}

	public List<BookingOverviewEntry> generateEntries() {
		filteredBookings = filterBookings();
		for (BookingView aBookingView : filteredBookings) {
			String effectivePurposeKey = getEffectivePurposeKey(aBookingView);
			if (mappedByEffectivePurposeKey.get(effectivePurposeKey) == null) {
				mappedByEffectivePurposeKey.put(effectivePurposeKey, new ArrayList<>());
			}
			mappedByEffectivePurposeKey.get(effectivePurposeKey).add(aBookingView);
		}
		List<BookingOverviewEntry> result = new ArrayList<>();
		
		for (String effectivePurposeKey : mappedByEffectivePurposeKey.keySet()) {
			result.add(new BookingOverviewEntry(effectivePurposeKey, mappedByEffectivePurposeKey.get(effectivePurposeKey)));
		}
		generatedEntries = result;
		return result;
	}

	protected abstract List<BookingView> filterBookings();

	private String getEffectivePurposeKey(BookingView aBookingView) {		
		if (!StringHelper.isBlank(aBookingView.getBookingPurposeKey())) {
			return aBookingView.getBookingPurposeKey();
		}
		return aBookingView.getPurposeKey();
	}
	
	public BookingView getReferenceBooking() {
		return referenceBooking;
	}
	
	public Account getAccount() {
		return account;
	}

	public BigDecimal sumTotal() {
		BigDecimal sum = BigDecimal.ZERO;
		for (BookingOverviewEntry aBookingOverviewEntry : generatedEntries) {
			sum = sum.add(aBookingOverviewEntry.sumAmount());
		}
		return sum;
	}

	public abstract String buildTitle();
	
	public List<BookingView> getFilteredBookings() {
		return filteredBookings;
	}
}