package persistence.querycommands;

import java.sql.Connection;
import java.sql.SQLException;

public interface QueryCommand<T> {
    /**
     * @param s the opened DB connection
     * @return the result
     * @throws SQLException content couldn't be loaded
     */
    T execute(Connection s) throws SQLException;
}
