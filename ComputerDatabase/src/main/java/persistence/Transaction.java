package persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import persistence.exceptions.DaoException;

public class Transaction {

    private static ThreadLocal<Connection> tconn = new ThreadLocal<Connection>();
    private DataSource ds;

    /**
     * @param ds HikariDataSource
     */
    public Transaction(DataSource ds) {
        this.ds = ds;
    }

    /**
     * @return get current ThreadLocal Connection
     * @throws DaoException connection could't be opened
     */
    public Connection open() throws DaoException {
        if (tconn.get() == null) {
            try {
                Connection conn = ds.getConnection();
                conn.setAutoCommit(false);
                tconn.set(conn);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return tconn.get();
    }

    /**
     * @param conn conn to commit, set auto-commit = true, and then release if needed
     * @throws DaoException failed to commit
     */
    public void release(Connection conn) throws DaoException {

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
    public Connection getCurrent() {
        return tconn.get();
    }

    /**
     * Beware, actually it doesn't check that the connection is closed.
     * It checks that the connection is the current transaction.
     * @param conn the connection to check
     * @return true if the connection is the currently open Transaction for this thread
     */
    public boolean isOpen(Connection conn) {
        return conn != null && conn == tconn.get();
    }
}
