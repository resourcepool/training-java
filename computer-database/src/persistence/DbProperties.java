package persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbProperties {
	private static final String CONFIG_PATH = "config.properties";
	private static final Properties INSTANCE = loadProperties();
	
	private DbProperties() {
		loadProperties();
	}

	private static Properties loadProperties() {
		Properties prop = new Properties();
		InputStream input = null;
		
		try 
		{
			input = DbProperties.class.getClassLoader().getResourceAsStream(CONFIG_PATH);
			prop.load(input);
		} 
		catch (IOException e) 
		{
			e.printStackTrace(); //TODO replace by logs
		} 
		finally 
		{
			if (input != null)
			{
				try 
				{
					input.close();
				} 
				catch (IOException io) 
				{
					io.printStackTrace(); //TODO replace by logs
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
