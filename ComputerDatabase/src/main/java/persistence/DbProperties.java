package persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbProperties {
    private static final Logger LOGGER      = LoggerFactory.getLogger(DbProperties.class);
    private static final String CONFIG_PATH = "config.properties";
    private static Properties   props;

    /**
     * private ctor.
     */
    private DbProperties() {
    }

    /**
     * @return the loaded properties
     * @throws IOException config failed to load
     */
    private static Properties loadProperties() throws IOException {
        Properties prop = new Properties();
        InputStream input = null;

        LOGGER.info("Loading db property : \"" + CONFIG_PATH + "\"");
        try {

            input = DbProperties.class.getClassLoader().getResourceAsStream(CONFIG_PATH);
            prop.load(input);

        } catch (IOException e) {

            LOGGER.error("Properties failed to load");
            throw e;

        } finally {

            if (input != null) {
                try {
                    input.close();
                } catch (IOException io) {
                    LOGGER.error("Properties io failed to close");
                }
            }
        }
        return prop;
    }

    /**
     * @throws IOException config failed to load
     */
    public static void load() throws IOException {
        if (props == null) {
            props = loadProperties();
        }
    }

    /**
     * @param key the key searched in loaded properties
     * @return the found property or null
     */
    public static String getConfig(String key) {

        return props.getProperty(key);
    }
}
