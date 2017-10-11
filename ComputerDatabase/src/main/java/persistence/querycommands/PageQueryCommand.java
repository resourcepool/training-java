package persistence.querycommands;

import java.sql.SQLException;
import java.util.List;

public interface PageQueryCommand<T> {

    /**
     * @param start start index to query
     * @param split number of elements to return
     * @return the content resulting of the query with @split elements
     * @throws SQLException content couldn't be loaded
     */
    List<T> getContent(Long start, Long split) throws SQLException;

}
