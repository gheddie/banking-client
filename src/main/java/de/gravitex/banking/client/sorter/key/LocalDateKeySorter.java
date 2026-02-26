package de.gravitex.banking.client.sorter.key;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import de.gravitex.banking.client.sorter.key.base.KeySorter;

public class LocalDateKeySorter extends KeySorter<LocalDate> {

	@Override
	public List<LocalDate> sortKey(Set<LocalDate> keySet) {
		List<LocalDate> dateList = new ArrayList<LocalDate>(keySet);
		Collections.sort(dateList, new LocalDateListSorter());
		return dateList;
	}
	
	private class LocalDateListSorter implements Comparator<LocalDate> {

		public int compare(LocalDate date1, LocalDate date2) {
			return date1.compareTo(date2) * (-1);
		}
	}
}