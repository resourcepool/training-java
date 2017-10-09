package persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbProperties {
	private static final String CONFIG_PATH = "config.properties";
	private static final Properties INSTANCE = loadProperties();
	
	private final static Logger logger = LoggerFactory.getLogger(DbProperties.class);
	private DbProperties() 
	{
		loadProperties();
	}

	private static Properties loadProperties() 
	{
		Properties prop = new Properties();
		InputStream input = null;
		
		try 
		{
			input = DbProperties.class.getClassLoader().getResourceAsStream(CONFIG_PATH);
			prop.load(input);
		} 
		catch (IOException e) 
		{
			logger.error("Properties failed to load");
		} 
		finally 
		{
			if (input != null)
			{
				try {
					input.close();
				} catch (IOException io) {
					logger.error("Properties io failed to load");
				}
			}
		}
		return prop;
	}
	
	public static String getConfig(String key)
	{
		return INSTANCE.getProperty(key);
	}
}
