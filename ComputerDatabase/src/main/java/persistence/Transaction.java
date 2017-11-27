package persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import persistence.exceptions.DaoException;

public class Transaction {

    private static ThreadLocal<Connection> tconn = new ThreadLocal<Connection>();

    /**
     * @return get current ThreadLocal Connection
     * @throws DaoException connection could't be opened
     */
    public static Connection openTransaction() throws DaoException {
        if (tconn.get() == null) {
            try {
                Connection conn = HikariPool.getConnection();
                conn.setAutoCommit(false);
                tconn.set(conn);
            } catch (SQLException | IOException e) {
                throw new DaoException(e);
            }
        }
        return tconn.get();
    }

    /**
     * @param conn conn to commit, set auto-commit = true, and then release if needed
     * @throws DaoException failed to commit
     */
    public static void releaseTransaction(Connection conn) throws DaoException {

        try {
            if (conn.isClosed() || tconn.get() != conn) {
                return;
            }
            conn.commit();
            conn.setAutoCommit(true);
            tconn.remove();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * @return saved transaction or null
     */
    public static Connection getCurrent() {
        return tconn.get();
    }

    /**
     * Beware, actually it doesn't check that the connection is closed.
     * It checks that the connection is the current transaction.
     * @param conn the connection to check
     * @return true if the connection is the currently open Transaction for this thread
     */
    public static boolean isOpen(Connection conn) {
        return conn != null && conn == tconn.get();
    }
}
