package de.gravitex.banking.client.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EntityTablePanel extends JPanel implements ListSelectionListener {

	private static final long serialVersionUID = 8638444496803145253L;
	
	private EntityTable table;

	private JLabel statusLabel;

	private String borderText;

	private List<?> entities;

	private EntityTablePanelListener tablePanelListener;
	
	private JList<ValueFilterItem> filterItemList;
	
	public EntityTablePanel(String aBorderText, EntityTablePanelListener aTablePanelListener, boolean aSingleSelection) {
		super();
		this.borderText = aBorderText;
		this.tablePanelListener = aTablePanelListener;
		setLayout(new BorderLayout());
		table = new EntityTable(aSingleSelection, this);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(this);		
		JPanel filterPanel = getFilterPanel();
		if (filterPanel != null) {
			add(GuiUtil.nestComponent(filterPanel, "Filter"), BorderLayout.WEST);	
		}		
		add(GuiUtil.nestComponent(table, borderText), BorderLayout.CENTER);
		statusLabel = new JLabel();
		add(statusLabel, BorderLayout.SOUTH);
	}

	private JPanel getFilterPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		filterItemList = new JList<ValueFilterItem>();
		filterItemList.setCellRenderer(new ValueFilterItemRenderer());
		panel.add(filterItemList, BorderLayout.CENTER);
		DefaultListModel<ValueFilterItem> model = new DefaultListModel<ValueFilterItem>();
		
		model.addElement(new ValueFilterItem());
		model.addElement(new ValueFilterItem());
		model.addElement(new ValueFilterItem());
		
		filterItemList.setModel(model);
		
		// return panel;
		return null;
	}

	public void displayEntities(List<?> aEntities) {		
		this.entities = aEntities;
		table.display(aEntities);
		statusLabel.setText(aEntities.size() + " Einträge");
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
	
	public void setEntities(List<?> entities) {
		this.entities = entities;
	}
	
	private class ValueFilterItemRenderer implements ListCellRenderer<ValueFilterItem> {

		public Component getListCellRendererComponent(JList<? extends ValueFilterItem> list, ValueFilterItem value,
				int index, boolean isSelected, boolean cellHasFocus) {
			// TODO Auto-generated method stub
			return value;
		}		
	}
}