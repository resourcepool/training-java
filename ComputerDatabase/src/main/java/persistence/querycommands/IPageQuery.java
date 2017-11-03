package persistence.querycommands;

import java.util.List;

import persistence.exceptions.DaoException;

public interface IPageQuery<T> {

    /**
     * @param start start index to query
     * @param split number of elements to return
     * @return the content resulting of the query with @split elements
     * @throws DaoException content couldn't be loaded
     */
    List<T> getContent(Long start, Long split) throws DaoException;

}
