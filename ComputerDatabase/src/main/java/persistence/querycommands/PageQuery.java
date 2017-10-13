package persistence.querycommands;

import java.util.List;

import mapper.exceptions.PageException;

public interface PageQuery<T> {

    /**
     * @param start start index to query
     * @param split number of elements to return
     * @return the content resulting of the query with @split elements
     * @throws PageException content couldn't be loaded
     */
    List<T> getContent(Long start, Long split) throws PageException;

}
