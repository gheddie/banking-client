package de.gravitex.banking.client.util;

import java.lang.reflect.Field;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;

public class ObjectUtil {

	public static boolean areValuesEqual(Object aValue1, Object aValue2) {
		if (aValue1 == null && aValue2 == null) {
			return true;
		} else if (aValue1 == null && aValue2 != null) {
			return false;
		} else if (aValue1 != null && aValue2 == null) {
			return false;
		}
		return (aValue1.equals(aValue2));
	}

	public static boolean areClassesEqual(Class<?> clazz1, Class<?> clazz2) {
		return areValuesEqual(clazz1, clazz2);
	}

	public static boolean isFieldNullable(Field aField) {
		return isFieldNullableByJoinColumn(aField);
	}

	private static boolean isFieldNullableByJoinColumn(Field aField) {
		JoinColumn column = aField.getAnnotation(JoinColumn.class);
		if (column == null) {
			return true;	
		}
		return column.nullable();
	}

	private static boolean isFieldNullableByColumn(Field aField) {
		Column column = aField.getAnnotation(Column.class);
		if (column == null) {
			return true;	
		}
		return column.nullable();
	}
}