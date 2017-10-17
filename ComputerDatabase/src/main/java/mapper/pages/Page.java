package mapper.pages;

import java.util.List;

import mapper.exceptions.PageException;
import persistence.querycommands.PageQuery;

public class Page<T> {

    private List<T>      content;
    private PageQuery<T> command;
    private Long         start;
    private Long         size;
    private Long         pageSize;

    /**
     * @param command query used to fill pages content, either dababase or cached entities
     * @param size number of total elements than can be loaded
     */
    public Page(PageQuery<T> command, Long size) {
        this(command, size, 10L);
    }

    /**
     * @param command query used to fill pages content, either dababase or cached entities
     * @param size number of total elements than can be loaded
     * @param pageSize number of elements by page
     */
    public Page(PageQuery<T> command, Long size, Long pageSize) {
        this(command, 0L, size, pageSize);
    }

    /**
     * @param command query used to fill pages content, either dababase or cached entities
     * @param start element index to start
     * @param size number of total elements than can be loaded
     * @param pageSize number of elements by page
     */
    public Page(PageQuery<T> command, Long start, Long size, Long pageSize) {
        this.pageSize = pageSize;
        this.content = null;
        this.start = start;
        this.size = size;
        this.command = command;
    }

    /**
     * @return return the current page loaded if it wasn't, else return the next page
     * @throws PageException page couldn't be loaded
     */
    public Page<T> next() throws PageException {
        if (!isLoaded()) {
            this.load();
            return this;
        }

        Page<T> p = new Page<T>(command, start + pageSize, size, pageSize);
        p.load();
        return p;
    }

    /**
     * @throws PageException PageException page couldn't be loaded
     */
    public void load() throws PageException {
        content = command.getContent(start, pageSize);
    }

    /**
     * @return concatenation of each .toString() loaded content
     */
    public String dump() {
        StringBuilder b = new StringBuilder();
        for (T t : content) {
            b.append(t.toString());
            b.append(System.lineSeparator());
        }
        return b.toString();
    }

    /**
     * @return Content or null if not loaded
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * @return true if this page is loaded and there is no next page
     */
    public Boolean hasNext() {
        return !isLoaded() || (start + pageSize < size);
    }

    public Long getCurrentCount() {
        return start;
    }

    public Long getTotalCount() {
        return size;
    }

    public Boolean isLoaded() {
        return content != null;
    }

    public Long getPageSize() {
        return content == null ? pageSize : content.size();
    }

    public Long getCurrentPage() {
        return (start / pageSize) + 1;
    }

    public Long getPageLimit() {
        return size / pageSize;
    }

}
