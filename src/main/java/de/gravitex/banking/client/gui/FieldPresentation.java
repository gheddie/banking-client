package de.gravitex.banking.client.gui;

import java.lang.reflect.Field;

public class FieldPresentation {

	private Field field;
	
	private int order;

	public FieldPresentation(Field aField, int aOrder) {
		super();
		this.field = aField;
		this.order = aOrder;
	}
	
	public int getOrder() {
		return order;
	}	
	
	public Field getField() {
		return field;
	}
}