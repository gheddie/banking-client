package de.gravitex.banking.client.gui.dialog.editor.item.generic;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import de.gravitex.banking.client.exception.BankingException;
import de.gravitex.banking.client.gui.dialog.editor.item.base.EditorItem;
import de.gravitex.banking.client.gui.dialog.editor.util.EditorItemListener;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.base.IdEntity;

public class GenericItemListEditorItem extends EditorItem implements ActionListener {

	public GenericItemListEditorItem(Field aField, IdEntity aEntity, EditorItemListener aEditorItemListener) {
		super(aField, aEntity, aEditorItemListener);
	}

	@Override
	public Component makeComponent() {
		JComboBox<IdEntity> comboBox = new JComboBox<>();
		comboBox.setModel(makeListModel());
		comboBox.addActionListener(this);
		return comboBox;
	}

	private ComboBoxModel<IdEntity> makeListModel() {
		DefaultComboBoxModel<IdEntity> model = new DefaultComboBoxModel<>();
		try {
			List<?> entities = ApplicationRegistry.getInstance().retrieveEntities(getField().getType());
			for (Object aEntity : entities) {
				model.addElement((IdEntity) aEntity);
			}
			return model;
		} catch (BankingException e) {
			e.printStackTrace();
			return null;
		}		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		IdEntity selectedEntity = (IdEntity) ((JComboBox<IdEntity>) e.getSource()).getSelectedItem();
		getEditorItemListener().onFieldValueChanged(selectedEntity, getField());
	}
}