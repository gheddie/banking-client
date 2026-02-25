package de.gravitex.banking.client.sorter.key;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import de.gravitex.banking.client.sorter.key.base.KeySorter;

public class StringKeySorter extends KeySorter<String> {

	@Override
	public List<String> sortKey(Set<String> keySet) {
		List<String> stringList = new ArrayList<String>(keySet);
		Collections.sort(stringList);
		return stringList;
	}
}