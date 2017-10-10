package persistence.querycommands;

import java.sql.SQLException;
import java.util.List;

public interface PageQueryCommand <T> {

	List<T> getContent(Long start, Long split) throws SQLException;

}
