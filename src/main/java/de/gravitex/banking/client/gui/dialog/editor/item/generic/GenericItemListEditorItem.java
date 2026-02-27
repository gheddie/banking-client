package de.gravitex.banking.client.gui.dialog.editor.item.generic;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import de.gravitex.banking.client.gui.dialog.editor.item.base.EditorItem;
import de.gravitex.banking.client.gui.dialog.editor.util.EditorItemListener;
import de.gravitex.banking_core.entity.base.IdEntity;

public class GenericItemListEditorItem extends EditorItem {

	public GenericItemListEditorItem(Field aField, IdEntity aEntity, EditorItemListener aEditorItemListener) {
		super(aField, aEntity, aEditorItemListener);
	}

	@Override
	public Component makeComponent() {
		JComboBox<Object> comboBox = new JComboBox<>();
		comboBox.setModel(makeListModel());
		return comboBox;
	}

	private ComboBoxModel<Object> makeListModel() {
		DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();
		Class<?> fieldType = getField().getType();
		return model;
	}
}