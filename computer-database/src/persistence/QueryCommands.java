package persistence;

import java.sql.Connection;
import java.sql.SQLException;

public interface QueryCommands <T> {
	T execute(Connection s) throws SQLException;
}
