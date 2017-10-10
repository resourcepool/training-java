package persistence.querycommands;

import java.sql.Connection;
import java.sql.SQLException;

public interface QueryCommand <T> {
	T execute(Connection s) throws SQLException;
}
