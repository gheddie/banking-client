package de.gravitex.banking.client.sorter.key.base;

import java.util.List;
import java.util.Set;

public abstract class KeySorter<T> {

	public abstract List<T> sortKey(Set<T> keySet); 
}