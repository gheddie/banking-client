package de.gravitex.banking.client.registry;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class StringTranslator {

	private Properties properties;

	public String translate(String aString) {
		String translated = (String) properties.get(aString);
		if (translated == null) {
			System.out.println("untranslated string: " + aString);
			return "<<<!!!" + aString + "!!!>>>";
		}
		return translated;
	}

	public String translate(Field aField) {
		String key = aField.getDeclaringClass().getSimpleName() + "." + aField.getName();
		return translate(key);
	}

	public void init() {
		properties = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		InputStream stream = loader.getResourceAsStream("translation.properties");
		try {
			properties.load(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}