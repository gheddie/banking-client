package de.gravitex.banking.client.sorter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.gravitex.banking.client.sorter.base.EntitySorter;
import de.gravitex.banking.client.sorter.key.DateKeySorter;
import de.gravitex.banking.client.sorter.key.StringKeySorter;
import de.gravitex.banking.client.sorter.key.base.KeySorter;
import de.gravitex.banking_core.entity.annotation.PresentMe;

public class DefaultEntitySorter implements EntitySorter {

	private Object entity;
	
	private Class<?> sortType;

	private static final Map<Class<?>, KeySorter> SORTERS = new HashMap<Class<?>, KeySorter>();
	static {
		SORTERS.put(Date.class, new DateKeySorter());
		SORTERS.put(String.class, new StringKeySorter());
	}

	public List<?> sortEntities(List<?> entites, Object firstEntity, List<Field> displayableFields) {
		this.entity = firstEntity;
		Field sortField = getSortField(displayableFields);		
		if (sortField == null) {
			return entites;	
		}
		sortType = sortField.getType();
		sortField.setAccessible(true);
		return sortByField(entites, sortField);
	}

	private List<?> sortByField(List<?> entites, Field sortField) {
		Map<Object, List<Object>> tmp = new HashMap<Object, List<Object>>();
		for (Object aEntity : entites) {
			try {
				Object sortKey = sortField.get(aEntity);
				if (tmp.get(sortKey) == null) {
					tmp.put(sortKey, new ArrayList<Object>());	
				}
				tmp.get(sortKey).add(aEntity);				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<Object> sorted = new ArrayList<Object>();
		for (Object aKey : sortKeys(tmp.keySet())) {
			sorted.addAll(tmp.get(aKey));
		}
		return sorted;
	}

	private List sortKeys(Set<Object> keySet) {
		KeySorter keySorter = SORTERS.get(sortType);
		if (keySorter == null) {
			throw new IllegalArgumentException("no key sorter defined for type " + sortType);
		}
		return keySorter.sortKey(keySet);
	}

	private Field getSortField(List<Field> displayableFields) {
		List<Field> list = new ArrayList<Field>();
		for (Field displayableField : displayableFields) {
			if (displayableField.getAnnotation(PresentMe.class).sortMe()) {
				list.add(displayableField);
			}
		}
		if (list.size() > 1) {
			throw new IllegalArgumentException("exactly ONE sortable field must be found ["+entity.getClass()+"]!!!");
		}
		if (list.size() == 1) {
			return list.get(0);	
		}
		return null;
	}
}