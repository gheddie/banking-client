package de.gravitex.banking.client.formatter;

import java.util.HashMap;
import java.util.Map;

import de.gravitex.banking_core.formatter.base.ValueFormatter;

public class ValueFormatterFactory {

	private Map<Class<? extends ValueFormatter>, ValueFormatter> formatters = new HashMap<Class<? extends ValueFormatter>, ValueFormatter>();

	public String format(Object aValue, Class<? extends ValueFormatter> formatterClass) {
		ValueFormatter formatter = formatters.get(formatterClass);
		if (formatter != null) {
			return formatter.format(aValue);
		} else {
			try {
				ValueFormatter createdFormatter = formatterClass.getConstructor(null).newInstance(null);
				formatters.put(formatterClass, createdFormatter);
				return createdFormatter.format(aValue);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}