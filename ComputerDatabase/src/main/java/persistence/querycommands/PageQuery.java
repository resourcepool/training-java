package persistence.querycommands;

import java.sql.SQLException;
import java.util.List;

import model.pages.Page;

public interface PageQuery<T> {

    /**
     * @param page page to use
     * @return the content resulting of the query with @split elements
     * @throws SQLException content couldn't be loaded
     */
    List<T> getContent(Page<T> page) throws SQLException;

}
