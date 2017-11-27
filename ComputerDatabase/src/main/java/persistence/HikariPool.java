package persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariPool {

    private static HikariConfig     config;
    private static HikariDataSource ds;

    /**
     * private ctor.
     */
    private HikariPool() {

    }

    /**
     * @return opened connection
     * @throws SQLException fail to connect
     * @throws IOException fail to open config
     */
    public static Connection getConnection() throws SQLException, IOException {
        if (ds == null) {
            loadConnections();
        }
        return ds.getConnection();
    }

    /**
     * init connections and config.
     * @throws IOException config failed to open
     */
    private static void loadConnections() throws IOException {
        Properties prop = new Properties();

        InputStream input = DbProperties.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(input);

        config = new HikariConfig(prop);
        ds = new HikariDataSource(config);
    }
}