package de.gravitex.banking.client.gui.filter.instance.base;

import java.awt.BorderLayout;
import java.lang.reflect.Field;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.gravitex.banking.client.registry.ApplicationRegistry;

public abstract class EntityFilter {
	
	private Field field;

	public EntityFilter(Field aField) {
		super();
		this.field = aField;
	}

	public JPanel makeComponent() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel(field.getName()), BorderLayout.CENTER);
		return panel;
	}
	
	public Field getField() {
		return field;
	}

	public String getTranslatedFieldName() {
		return ApplicationRegistry.getInstance().getStringTranslator().translate(field);
	}

	public abstract boolean doAcceptValue(Object aValue);

	public boolean acceptValue(Object entityValue) {
		/*
		boolean hasInfo = hasFilterInfo();
		if (!hasInfo) {
			return true;
		}
		*/
		boolean result = doAcceptValue(entityValue);
		return result;
	}

	protected abstract boolean hasFilterInfo();

	public abstract String formatFilterInfo();
}