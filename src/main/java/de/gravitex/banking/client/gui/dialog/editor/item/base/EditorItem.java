package de.gravitex.banking.client.gui.dialog.editor.item.base;

import java.awt.Component;
import java.lang.reflect.Field;

import de.gravitex.banking.client.gui.dialog.editor.util.EditorItemListener;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.base.IdEntity;

public abstract class EditorItem {
	
	private Field field;
	
	private IdEntity entity;

	private EditorItemListener editorItemListener;
	
	public EditorItem(Field aField, IdEntity aEntity, EditorItemListener aEditorItemListener) {
		super();
		this.field = aField;
		this.entity = aEntity;
		this.editorItemListener = aEditorItemListener;
	}

	public String getLabelText() {
		return ApplicationRegistry.getInstance().getStringTranslator().translate(field);
	}

	public abstract Component makeComponent();
	
	protected Object getFieldValue() {
		field.setAccessible(true);
		try {
			return field.get(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected EditorItemListener getEditorItemListener() {
		return editorItemListener;
	}
	
	public Field getField() {
		return field;
	}

	public abstract void disableEdit();
}