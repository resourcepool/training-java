package persistence.querycommands;

import java.util.List;

import model.pages.Page;
import persistence.exceptions.DaoException;

public interface PageQuery<T> {

    /**
     * @param page page to use
     * @return the content resulting of the query with @split elements
     * @throws DaoException content couldn't be loaded
     */
    List<T> getContent(Page<T> page) throws DaoException;

}
