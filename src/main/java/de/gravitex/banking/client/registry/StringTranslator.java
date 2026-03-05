package de.gravitex.banking.client.registry;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringTranslator {
	
	private Logger logger = LoggerFactory.getLogger(StringTranslator.class);

	private Properties properties;

	public String translate(String aString) {
		String translated = (String) properties.get(aString);
		if (translated == null) {
			logger.info("untranslated string: " + aString);
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