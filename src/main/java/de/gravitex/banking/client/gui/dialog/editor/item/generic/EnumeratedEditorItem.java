package de.gravitex.banking.client.gui.dialog.editor.item.generic;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import de.gravitex.banking.client.gui.dialog.editor.item.base.EditorItem;
import de.gravitex.banking.client.gui.dialog.editor.util.EditorItemListener;
import de.gravitex.banking_core.entity.base.IdEntity;

public class EnumeratedEditorItem extends EditorItem implements ActionListener {

	private Class<?> enumType;

	public EnumeratedEditorItem(Field aField, IdEntity aEntity, EditorItemListener aEditorItemListener) {
		super(aField, aEntity, aEditorItemListener);
		this.enumType = aField.getType();
	}

	@Override
	public Component makeComponent() {
		JComboBox<Object> comboBox = new JComboBox<>();
		comboBox.setModel(makeListModel());
		comboBox.addActionListener(this);
		return comboBox;
	}

	private ComboBoxModel<Object> makeListModel() {
		Object[] enumConstants = enumType.getEnumConstants();
		DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();
		model.addElement(null);
		for (Object aEnumConstant : enumConstants) {
			model.addElement(aEnumConstant);
		}		
		return model;
	}

	@Override
	public void disableEdit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void syncValue() {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		Object selectedEnumClass = ((JComboBox<Object>) e.getSource()).getSelectedItem();
		getEditorItemListener().onFieldValueChanged(selectedEnumClass, getField());		
	}
}