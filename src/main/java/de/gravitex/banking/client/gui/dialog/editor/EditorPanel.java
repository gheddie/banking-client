package de.gravitex.banking.client.gui.dialog.editor;

import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.gravitex.banking.client.gui.dialog.editor.item.base.EditorItem;
import de.gravitex.banking.client.gui.dialog.editor.item.factory.EditorItemFactory;
import de.gravitex.banking.client.gui.dialog.editor.util.EditorItemListener;
import de.gravitex.banking_core.entity.base.IdEntity;

public class EditorPanel extends JPanel implements EditorItemListener {
	
	private static final long serialVersionUID = 1385245399458480102L;
	
	private IdEntity entity;

	private EditorItemFactory factory = new EditorItemFactory();

	private EntityEditorDialog entityEditorDialog;

	public EditorPanel(IdEntity aEntity, EntityEditorDialog aEntityEditorDialog) {
		super();
		this.entity = aEntity;
		this.entityEditorDialog = aEntityEditorDialog;
		List<EditorItem> editorItems = getEditorItems();
		setLayout(new GridLayout(editorItems.size(), 2));
		for (EditorItem aEditorItem : editorItems) {
			add(new JLabel(aEditorItem.getLabelText()));
			add(aEditorItem.makeComponent());
		}
	}

	private List<EditorItem> getEditorItems() {
		ArrayList<EditorItem> items = new ArrayList<>();
		for (Field aField : entity.getClass().getDeclaredFields()) {
			EditorItem item = factory.getEditorItem(aField, entity, this);
			if (item != null) {
				items.add(item);
			}
		}
		return items;
	}

	@Override
	public void onFieldValueChanged(Object aChangedValue, Field aField) {
		System.out.println("["+aField.getName()+"] onFieldValueChanged --> " + aChangedValue);
		entityEditorDialog.markDirty();
		updateFieldValue(aField, aChangedValue);
	}

	private void updateFieldValue(Field aField, Object aChangedValue) {
		aField.setAccessible(true);
		try {
			aField.set(entity, aChangedValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}