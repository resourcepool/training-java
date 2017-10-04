package persistence;

import java.sql.SQLException;
import java.sql.Statement;

public interface QueryCommands <T> {
	T execute(Statement s) throws SQLException;
}
