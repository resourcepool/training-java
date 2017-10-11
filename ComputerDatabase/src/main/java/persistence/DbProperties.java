package persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbProperties {
    private static final Logger LOGGER      = LoggerFactory.getLogger(DbProperties.class);
    private static final String CONFIG_PATH = "config.properties";
    private static Properties   instance;

    /**
     * private ctor.
     */
    private DbProperties() {
        loadProperties();
    }

    /**
     * @return the loaded properties
     */
    private static Properties loadProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = DbProperties.class.getClassLoader().getResourceAsStream(CONFIG_PATH);
            prop.load(input);
        } catch (IOException e) {

            LOGGER.error("Properties failed to load");

        } finally {

            if (input != null) {
                try {
                    input.close();
                } catch (IOException io) {
                    LOGGER.error("Properties io failed to load");
                }
            }

        }
        return prop;
    }

    /**
     * @param key the key searched in loaded properties
     * @return the found property or null
     */
    public static String getConfig(String key) {
        return instance.getProperty(key);
    }
}
