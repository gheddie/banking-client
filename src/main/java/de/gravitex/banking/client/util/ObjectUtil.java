package de.gravitex.banking.client.util;

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
}