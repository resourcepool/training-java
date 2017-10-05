package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultMapper<T> {
	T process(ResultSet set) throws SQLException; 
}
