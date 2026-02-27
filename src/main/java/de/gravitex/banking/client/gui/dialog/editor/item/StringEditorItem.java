package de.gravitex.banking.client.gui.dialog.editor.item;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import de.gravitex.banking.client.gui.dialog.editor.item.base.EditorItem;
import de.gravitex.banking.client.gui.dialog.editor.util.EditorItemListener;
import de.gravitex.banking_core.entity.base.IdEntity;

public class StringEditorItem extends EditorItem implements CaretListener {

	private JTextField textField;

	public StringEditorItem(Field aField, IdEntity aEntity, EditorItemListener aEditorItemListener) {
		super(aField, aEntity, aEditorItemListener);
	}

	@Override
	public Component makeComponent() {
		textField = new JTextField((String) getFieldValue());
		textField.addCaretListener(this);
		return textField;
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		String changedValue = ((JTextField) e.getSource()).getText();
		getEditorItemListener().onFieldValueChanged(changedValue, getField());
	}
	
	@Override
	public void disableEdit() {
		textField.setEditable(false);
	}
}