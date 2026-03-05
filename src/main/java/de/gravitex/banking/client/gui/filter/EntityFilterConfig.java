package de.gravitex.banking.client.gui.filter;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gravitex.banking.client.gui.filter.instance.BigDecimalRangeEntityFilter;
import de.gravitex.banking.client.gui.filter.instance.StringEntityFilter;
import de.gravitex.banking.client.gui.filter.instance.base.EntityFilter;

public class EntityFilterConfig {
	
	private Logger logger = LoggerFactory.getLogger(EntityFilterConfig.class);
	
	private static final Map<Class<?>, Class<?>> FILTER_CLASS_MAPPING = new HashMap<>();
	static {
		FILTER_CLASS_MAPPING.put(String.class, StringEntityFilter.class);
		FILTER_CLASS_MAPPING.put(BigDecimal.class, BigDecimalRangeEntityFilter.class);
	}
	
	private Map<String, EntityFilter> filtersByField = new HashMap<>();

	private List<EntityFilter> filters = new ArrayList<>();

	public void addEntityFilter(Field aField) {
		EntityFilter filterInstance = makeFilterInstance(aField);
		if (filterInstance != null) {
			filters.add(filterInstance);
			filtersByField.put(aField.getName(), filterInstance);
		}		
	}

	private EntityFilter makeFilterInstance(Field aField) {
		Class<?> type = FILTER_CLASS_MAPPING.get(aField.getType());
		if (type == null) {
			return null;
		} 
		try {
			EntityFilter filterInstance = (EntityFilter) type.getDeclaredConstructor(new Class[] { Field.class })
					.newInstance(new Object[] { aField });
			return filterInstance;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public boolean hasFilters() {
		return !filters.isEmpty();
	}

	public int getFilterCount() {
		return filters.size();
	}
	
	public List<EntityFilter> getFilters() {
		return filters;
	}

	public boolean accept(Object entity) {
		for (String aFieldName : filtersByField.keySet()) {
			EntityFilter entityFilter = filtersByField.get(aFieldName);
			Object entityFieldValue = getEntityFieldValue(entity, entityFilter.getField());
			boolean accepted = entityFilter.acceptValue(entityFieldValue);
			String formattedFilterInfo = entityFilter.formatFilterInfo();
			if (!accepted) {
				logger.info("@@@@@@@@@@@@@@@@@@@@@ filter (" + entityFilter.getClass().getSimpleName() + ") [" + aFieldName
						+ "] (" + formattedFilterInfo + ") to accept --> " + entityFieldValue + " [NOT]");
				return false;
			} else {
				logger.info("@@@@@@@@@@@@@@@@@@@@@ filter (" + entityFilter.getClass().getSimpleName() + ") [" + aFieldName
						+ "] (" + formattedFilterInfo + ") to accept --> " + entityFieldValue + " [YES]");
			}
		}
		return true;
	}

	private Object getEntityFieldValue(Object entity, Field aField) {
		aField.setAccessible(true);
		try {
			return aField.get(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}