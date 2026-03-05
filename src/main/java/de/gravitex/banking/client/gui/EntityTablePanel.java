package de.gravitex.banking.client.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gravitex.banking.client.gui.action.DeleteTableContextAction;
import de.gravitex.banking.client.gui.action.EditTableContextAction;
import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.gui.filter.EntityFilterConfig;
import de.gravitex.banking.client.gui.filter.instance.base.EntityFilter;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.client.util.ObjectUtil;
import de.gravitex.banking_core.entity.annotation.Creatable;
import de.gravitex.banking_core.entity.base.NoIdEntity;

public class EntityTablePanel extends JPanel implements ListSelectionListener, EntityTableListener, ActionProvider {
	
	private Logger logger = LoggerFactory.getLogger(EntityTablePanel.class);

	private static final long serialVersionUID = 8638444496803145253L;
	
	private EntityTable table;

	private JLabel statusLabel;

	private String borderText;

	private List<?> entities;

	private EntityTablePanelListener tablePanelListener;
	
	private Class<? extends NoIdEntity> entityClass;

	private EntityFilterConfig filterConfig;

	private JButton createEntity;

	public EntityTablePanel(String aBorderText, EntityTablePanelListener aTablePanelListener, boolean aSingleSelection,
			Class<? extends NoIdEntity> aEntityClass) {
		super();
		this.borderText = aBorderText;
		this.tablePanelListener = aTablePanelListener;
		this.entityClass = aEntityClass;
		setLayout(new BorderLayout());
		table = new EntityTable(aSingleSelection, this);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(this);
		add(addNewButton(getCenterComponent()), BorderLayout.CENTER);
		statusLabel = new JLabel();
		add(statusLabel, BorderLayout.SOUTH);
	}

	private JPanel addNewButton(Component aCenterComponent) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(aCenterComponent, BorderLayout.CENTER);
		if (entityCreatable()) {
			createEntity = new JButton("Neu");
			createEntity.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					createEntity();
				}
			});
			panel.add(createEntity, BorderLayout.EAST);	
		}		
		return panel;
	}
	
	private void createEntity() {
		ApplicationRegistry.getInstance().getCrudHandler().createEntity(entityClass, this);
	}

	private boolean entityCreatable() {
		return (entityClass.getAnnotation(Creatable.class) != null);
	}

	private Component getCenterComponent() {
		JPanel nestedTable = GuiUtil.nestComponent(table, borderText);
		JPanel filterPanel = getFilterPanel();
		if (filterPanel != null) {			
			JPanel nestedFilter = GuiUtil.nestComponent(filterPanel, "Filter");
			return GuiUtil.getSplitPane(nestedFilter, nestedTable, true);
		} else {			
			return nestedTable;	
		}
	}

	private JPanel getFilterPanel() {
		
		filterConfig = ApplicationRegistry.getInstance().getFilterProvider().getFilterConfig(entityClass);
		if (!filterConfig.hasFilters()) {
			return null;
		}
		JPanel panel = new JPanel();
		GridLayout gridLayout = new GridLayout(filterConfig.getFilterCount() + 1, 1);
		panel.setLayout(gridLayout);
		for (EntityFilter aEntityFilter : filterConfig.getFilters()) {
			panel.add(GuiUtil.nestComponent(aEntityFilter.makeComponent(), aEntityFilter.getTranslatedFieldName()));
		}
		JButton applyFilter = new JButton("Anwenden");
		applyFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Filter anwenden...");
				filterEntities();
			}
		});
		panel.add(applyFilter);
		return panel;		
	}
	
	private void filterEntities() {		
		entities = tablePanelListener.reloadEntities(entityClass);		
		List<Object> filtered = new ArrayList<>();
		if (entities == null) {
			return;
		}
		logger.info("filtering {" + entities.size() + "} entities...");
		for (Object entity : entities) {			
			if (filterConfig.accept(entity)) {
				filtered.add(entity);
			} else {
				logger.info("entity NOT accepted by filter --> " + entity);
			}
		}
		logger.info("filtered {" + filtered.size() + "} entities...");
		statusLabel.setText(filtered.size() + " Einträge");
		table.display(filtered);
	}

	public void displayEntities(List<?> aEntities) {		
		this.entities = aEntities;
		checkEntityClasses();
		table.display(aEntities);
		statusLabel.setText(aEntities.size() + " Einträge");
	}

	private void checkEntityClasses() {
		for (Object aEntity : entities) {
			if (!ObjectUtil.areClassesEqual(aEntity.getClass(), entityClass)) {
				throw new IllegalArgumentException("expected entity of class [" + entityClass.getCanonicalName()
						+ "], but got [" + aEntity.getClass().getCanonicalName() + "]!!!");
			}
		}
	}

	public void valueChanged(ListSelectionEvent e) {				
		if (e.getValueIsAdjusting()) {
			try {
				Object aEntity = entities.get(table.getSelectedRow());
				tablePanelListener.onEntitySelected(aEntity);	
			} catch (Exception e2) {
				System.err.println(e2.getMessage());
			}			
		}		
	}

	public void handleDoubeClick(int selectedRow) {
		tablePanelListener.onEntityDoubeClicked(entities.get(selectedRow));
	}
	
	public void acceptEntities(List<?> entities) {
		this.entities = entities;
	}
	
	private class ValueFilterItemRenderer implements ListCellRenderer<ValueFilterItem> {

		public Component getListCellRendererComponent(JList<? extends ValueFilterItem> list, ValueFilterItem value,
				int index, boolean isSelected, boolean cellHasFocus) {
			// TODO Auto-generated method stub
			return value;
		}		
	}

	@Override
	public List<TableContextAction<?>> getContextActions() {
		List<TableContextAction<?>> actions = new ArrayList<>();
		actions.add(new EditTableContextAction(this));
		actions.add(new DeleteTableContextAction(this));
		return actions;
	}

	@Override
	public Object getContextObject() {
		return tablePanelListener.getSelectedObject();
	}

	@Override
	public Window getWindow() {
		return ApplicationRegistry.getInstance().getParentView();
	}

	@Override
	public Object getInvoker() {
		return tablePanelListener;
	}

	@Override
	public Class<? extends NoIdEntity> getEntityClass() {
		return entityClass;
	}

	@Override
	public EntityTablePanelListener getPanelListener() {
		return tablePanelListener;
	}
}