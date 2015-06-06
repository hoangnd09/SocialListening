package com.jp.asilla.social.listening;

import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.jp.asilla.social.listening.exception.SLConfigurationException;

/**
 * Read configuration in src/main/resource/setting.xml
 */
public class SLSetting {

	public static final String DAO_IMPLEMENT = "dao.implement";
	public static final String COLLECTOR_PREFIX = "collector.network.";
	
	private static final String CONFIGURED_RESOURCE = "setting.xml";
	private static Properties properties;

	private static void load() throws SLConfigurationException {
		try {
			properties = new Properties();
			InputStream stream = SLSetting.class.getClassLoader().getResourceAsStream(CONFIGURED_RESOURCE);
			properties.loadFromXML(stream);
		} catch (InvalidPropertiesFormatException e) {
			throw new SLConfigurationException(e);
		} catch (IOException e) {
			throw new SLConfigurationException(e);
		}
	}
	
	public static String getSetting(String key, String defaultValue) {
		try {
			return getSetting(key);
		} catch (SLConfigurationException e) {
			return defaultValue;
		}
	}
	
	public static String getSetting(String key) throws SLConfigurationException {
		if (properties == null) {
			load();
		}
		
		if(properties.containsKey(key)) {
			return properties.getProperty(key);
		} else {
			throw new SLConfigurationException(String.format("setting for %s is not fould in %s", key, CONFIGURED_RESOURCE));
		}
	}
	
	public static String getCollectorKey(String network) {
		return String.format("%s%s", COLLECTOR_PREFIX, network);
	}
}
