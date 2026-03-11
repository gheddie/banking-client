package de.gravitex.banking.client.gui.filter;

import java.lang.reflect.Field;

import de.gravitex.banking.entity.annotation.PresentMe;

public class EntityFilterProvider {

	public EntityFilterConfig getFilterConfig(Class<?> aEntityClass) {
		EntityFilterConfig filterConfig = new EntityFilterConfig();
		for (Field aField : aEntityClass.getDeclaredFields()) {
			PresentMe presentMe = aField.getAnnotation(PresentMe.class);
			if (presentMe != null) {
				if (presentMe.filterMe()) {
					filterConfig.addEntityFilter(aField);
				}	
			}			
		}				
		return filterConfig;
	}
}