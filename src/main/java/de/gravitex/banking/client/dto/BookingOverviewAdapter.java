package de.gravitex.banking.client.dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.gravitex.banking.entity.Booking;
import de.gravitex.banking_core.dto.BookingOverview;
import de.gravitex.banking_core.dto.BookingOverviewPurposeKey;

public class BookingOverviewAdapter {

	private BookingOverview bookingOverview;
	
	private Map<String, BookingOverviewPurposeKey> mappedPurposeKeys = new HashMap<>();

	public BookingOverviewAdapter(BookingOverview aBookingOverview) {
		super();
		this.bookingOverview = aBookingOverview;
		mapPurposeKeys();
	}

	private void mapPurposeKeys() {
		for (BookingOverviewPurposeKey aOverview : bookingOverview.getBookingOverviewTradingKeys()) {
			mappedPurposeKeys.put(aOverview.getPurposeKey(), aOverview);
		}
	}

	public Set<String> getAssignedPurposeKeys() {
		Set<String> result = new HashSet<>();
		for (String aKey : mappedPurposeKeys.keySet()) {
			if (aKey != null) {
				result.add(aKey);
			}
		}
		return result;
	}

	public BigDecimal getSum(String aPurposeKey) {
		return mappedPurposeKeys.get(aPurposeKey).getTotalSum();
	}

	public BigDecimal getUnassignedSum() {
		return mappedPurposeKeys.get(null).getTotalSum();		
	}

	public List<Booking> getBookings(String aPurposeKey) {
		return mappedPurposeKeys.get(aPurposeKey).getBookings();
	}

	public int countBookings() {
		int count = 0;
		for (String aKey : mappedPurposeKeys.keySet()) {
			count += mappedPurposeKeys.get(aKey).getBookingCount();
		}
		return count;
	}

	public String buildTitle() {
		return "Buchungs-Übersicht für Konto ("+bookingOverview.getAccount().getName()+")";
	}

	public BigDecimal buildSumTotal() {
		BigDecimal result = BigDecimal.ZERO;
		for (String aKey : mappedPurposeKeys.keySet()) {
			result = result.add(mappedPurposeKeys.get(aKey).getTotalSum());	
		}
		return result;
	}
}