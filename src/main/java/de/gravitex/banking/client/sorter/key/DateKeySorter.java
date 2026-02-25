package de.gravitex.banking.client.sorter.key;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import de.gravitex.banking.client.sorter.key.base.KeySorter;

public class DateKeySorter extends KeySorter<Date> {

	@Override
	public List<Date> sortKey(Set<Date> keySet) {
		List<Date> dateList = new ArrayList<Date>(keySet);
		Collections.sort(dateList, new DateListSorter());
		return dateList;
	}
	
	private class DateListSorter implements Comparator<Date> {

		public int compare(Date date1, Date date2) {
			return date1.compareTo(date2) * (-1);
		}		
	}
}