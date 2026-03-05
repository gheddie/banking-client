package de.gravitex.banking.client.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.client.sorter.base.EntitySorter;
import de.gravitex.banking_core.entity.annotation.PresentMe;

public class EntityTable extends JTable {

	private static final long serialVersionUID = -5242840734965330695L;
	
	private boolean singleSelection;

	private EntityTableListener entityTableListener;
	
	public EntityTable(boolean aSingleSelection, EntityTableListener aEntityTableListener) {
		super();
		this.singleSelection = aSingleSelection;
		this.entityTableListener = aEntityTableListener;
		setComponentPopupMenu(initActions());
		if (singleSelection) {
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2) {
					entityTableListener.handleDoubeClick(getSelectedRow());
				}
			}
		});
	}

	private JPopupMenu initActions() {
		List<TableContextAction<?>> actions = ApplicationRegistry.getInstance().getActionFactory()
				.getContextActions((ActionProvider) entityTableListener, entityTableListener.getEntityClass());
		JPopupMenu menu = new JPopupMenu();
		for (TableContextAction<?> aTableContextAction : filterActions(actions)) {
			JMenuItem menuItem = new JMenuItem(aTableContextAction.getActionText());
			menuItem.addActionListener(aTableContextAction);
			menu.add(menuItem);
		}
		return menu;
	}

	@SuppressWarnings("unchecked")
	private List<TableContextAction<?>> filterActions(List<TableContextAction<?>> actions) {
		EntityTablePanelListener panelListener = entityTableListener.getPanelListener();
		ActionFilter actionFilter = panelListener.getActionFilter();
		if (actionFilter == null) {
			return actions;	
		}
		List<TableContextAction<?>> result = new ArrayList<>();
		for (TableContextAction<?> action : actions) {
			if (!actionFilter.isActionFiltered((Class<? extends TableContextAction<?>>) action.getClass())) {
				result.add(action);
			}
		}
		return result;
	}

	public void display(List<?> entities) {				
		setModel(buildModel(entities));
	}

	private TableModel buildModel(List<?> entites) {		
		if (entites == null || entites.isEmpty()) {
			return new DefaultTableModel(new Object[0][0], new Object[0]);
		}		
		Object firstEntity = entites.get(0);
		List<Field> fields = getPresentableFields(firstEntity);
		List<?> sorted = sort(entites, firstEntity, fields);
		entityTableListener.acceptEntities(sorted);
		return new ReadOnlyTableModel(buildData(fields, sorted), buildColumnNames(fields));
	}

	private List<?> sort(List<?> entites, Object firstEntity, List<Field> displayableFields) {
		EntitySorter sorter = ApplicationRegistry.getInstance().getEntitySorter(firstEntity.getClass());
		if (sorter == null) {
			return entites;
		}
		return sorter.sortEntities(entites, firstEntity, displayableFields);
	}

	private List<Field> getPresentableFields(Object firstEntity) {
		List<FieldPresentation> fieldPresentations = new ArrayList<FieldPresentation>();
		for (Field field : firstEntity.getClass().getDeclaredFields()) {
			if (field.getAnnotation(PresentMe.class) != null) {
				fieldPresentations.add(new FieldPresentation(field, field.getAnnotation(PresentMe.class).order()));	
			}			
		}
		if (fieldPresentations.isEmpty()) {
			throw new IllegalArgumentException("no presentable field determined for entity ["
					+ firstEntity.getClass().getCanonicalName() + "]!!!");
		}
		return sortFields(fieldPresentations, firstEntity);
	}

	private List<Field> sortFields(List<FieldPresentation> aFieldPresentations, Object firstEntity) {
		Map<Integer, FieldPresentation> tmp = new HashMap<Integer, FieldPresentation>();
		for (FieldPresentation aFieldPresentation : aFieldPresentations) {
			if (tmp.get(aFieldPresentation.getOrder()) != null) {
				throw new IllegalArgumentException("field index ["+aFieldPresentation.getOrder()+"] duplicate in class ["+firstEntity.getClass()+"]!!!");
			}
			tmp.put(aFieldPresentation.getOrder(), aFieldPresentation);
		}
		List<Integer> orderList = new ArrayList<Integer>(tmp.keySet());
		Collections.sort(orderList);
		List<Field> result = new ArrayList<Field>();
		for (Integer aOrderKey : orderList) {
			result.add(tmp.get(aOrderKey).getField());
		}
		return result;
	}

	private Object[] buildColumnNames(List<Field> fields) {
		List<String> fieldNames = new ArrayList<String>();
		for (Field f : fields) {			
			fieldNames.add(ApplicationRegistry.getInstance().getStringTranslator().translate(f));
		}
		return fieldNames.toArray(new String[fieldNames.size()]);
	}

	private Object[][] buildData(List<Field> fields, List<?> entites) {
		Object[][] data = new Object[entites.size()][fields.size()];
		int row = 0;
		for (Object e : entites) {
			int col = 0;
			for (Field f : fields) {
				data[row][col] = getValue(e, f);
				col++;
			}
			row++;
		}
		return data;
	}

	private String getValue(Object entity, Field field) {
		field.setAccessible(true);
		try {
			return ApplicationRegistry.getInstance().formatValue(field.get(entity),
					field.getAnnotation(PresentMe.class).valueFormatter());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}