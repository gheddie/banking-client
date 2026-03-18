package de.gravitex.banking.client.gui.action.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.util.ActionProvider;

public class ActionFactory {

	private Map<Class<?>, List<Class<? extends TableContextAction<?>>>> actionMap = new HashMap<>();

	public List<TableContextAction<?>> getContextActions(ActionProvider actionProvider,
			Class<?> actionObjectClass) {
		List<TableContextAction<?>> actions = new ArrayList<>();
		List<Class<? extends TableContextAction<?>>> actionClassList = actionMap.get(actionObjectClass);
		if (actionClassList != null) {
			for (Class<? extends TableContextAction<?>> actionClass : actionClassList) {
				actions.add(makeActionInstance(actionClass, actionProvider));
			}
		}
		return actions;
	}

	private TableContextAction<?> makeActionInstance(Class<? extends TableContextAction<?>> actionClass,
			ActionProvider actionProvider) {
		try {
			return actionClass.getConstructor(new Class[] { ActionProvider.class })
					.newInstance(new Object[] { actionProvider });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void registerAction(Class<?> aEntityClass,
			Class<? extends TableContextAction<?>> actionClass) {
		if (actionMap.get(aEntityClass) == null) {
			actionMap.put(aEntityClass, new ArrayList<>());
		}
		actionMap.get(aEntityClass).add(actionClass);
	}
}