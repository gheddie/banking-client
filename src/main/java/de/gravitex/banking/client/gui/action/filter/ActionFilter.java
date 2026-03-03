package de.gravitex.banking.client.gui.action.filter;

import java.util.HashSet;
import java.util.Set;

import de.gravitex.banking.client.gui.action.base.TableContextAction;

public class ActionFilter {

	private Set<Class<? extends TableContextAction<?>>> filteredActions = new HashSet<>();
	
	@SuppressWarnings("unchecked")
	private ActionFilter(Class<? extends TableContextAction<?>>... aFilteredActions) {
		super();
		if (aFilteredActions != null) {
			for (Class<? extends TableContextAction<?>> actionClass : aFilteredActions) {
				this.filteredActions.add(actionClass);	
			}			
		}		
	}

	public boolean isActionFiltered(Class<? extends TableContextAction<?>> actionClass) {
		if (filteredActions == null) {
			return false;
		}
		return filteredActions.contains(actionClass);
	}

	public static ActionFilter forActions(Class<? extends TableContextAction<?>>... aFilteredActions) {
		return new ActionFilter(aFilteredActions);
	}
}