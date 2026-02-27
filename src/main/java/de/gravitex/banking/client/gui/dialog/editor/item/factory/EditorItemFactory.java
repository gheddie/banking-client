package de.gravitex.banking.client.gui.dialog.editor.item.factory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import de.gravitex.banking.client.gui.dialog.editor.item.StringEditorItem;
import de.gravitex.banking.client.gui.dialog.editor.item.base.EditorItem;
import de.gravitex.banking.client.gui.dialog.editor.item.generic.GenericItemListEditorItem;
import de.gravitex.banking.client.gui.dialog.editor.util.EditorItemListener;
import de.gravitex.banking_core.entity.base.IdEntity;

public class EditorItemFactory {

	private static final Map<Class<?>, Class<? extends EditorItem>> ITEM_STUBS = new HashMap<>();
	static {
		ITEM_STUBS.put(String.class, StringEditorItem.class);
	}

	public EditorItem getEditorItem(Field aField, IdEntity entity, EditorItemListener aEditorItemListener) {
		Class<?> fieldType = aField.getType();
		if (IdEntity.class.isAssignableFrom(fieldType)) {
			return new GenericItemListEditorItem(aField, entity, aEditorItemListener);
		}
		Class<? extends EditorItem> clazz = ITEM_STUBS.get(fieldType);
		if (clazz == null) {
			System.out.println("ITEM IGNORED --> " + fieldType);
			return null;
		}
		return makeInstance(clazz, aField, entity, aEditorItemListener);
	}

	private EditorItem makeInstance(Class<? extends EditorItem> clazz, Field aField, IdEntity aEntity, EditorItemListener aEditorItemListener) {
		try {
			return clazz.getConstructor(new Class[] { Field.class, IdEntity.class, EditorItemListener.class })
					.newInstance(new Object[] { aField, aEntity, aEditorItemListener });
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}