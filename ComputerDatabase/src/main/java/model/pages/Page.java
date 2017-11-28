package model.pages;

import java.sql.SQLException;
import java.util.List;

import mapper.ComputerMapping;
import persistence.querycommands.PageQuery;
import service.PageRequest;
import service.PageUtils;

public class Page<T> {

    private PageQuery<T>    command;
    private List<T>         content;
    private Long            total;

    private Long            pageSize;
    private Long            nbPage;
    private String          search;
    private ComputerMapping columnSort;
    private String          order;

    /**
     * Contruct from a page builder.
     *
     * @param pageQuery query to get content
     * @param total total of elem
     * @param pageBuilder builder
     */
    public Page(PageQuery<T> pageQuery, Long total, PageRequest<T> pageBuilder) {
        this(pageQuery, total);
        this.nbPage = pageBuilder.getNbPage();
        this.pageSize = pageBuilder.getPageSize();
        this.search = pageBuilder.getSearch();
        this.order = pageBuilder.getOrder();
        this.columnSort = pageBuilder.getColumnSort();
    }

    /**
     * Contruct from an already existing page.
     *
     * @param pageQuery query to get content
     * @param total total of elem
     * @param page page
     * @param nbPage nbpage (starting a 1)
     */
    public Page(PageQuery<T> pageQuery, Long total, Page<T> page, Long nbPage) {
        this(pageQuery, total);

        this.nbPage = nbPage;
        this.pageSize = page.getPageSize();
        this.search = page.getSearch();
        this.order = page.getOrder();
        this.columnSort = page.getColumnSort();
    }

    /**
     * Build a page without params (search, sort, order).
     * @param query query
     * @param total nb of elem in DB
     * @param pageSize nb of elem / page
     * @param nbPage current page (starting a 1)
     */
    public Page(PageQuery<T> query, Long total, Long pageSize, Long nbPage) {
        this(query, total);
        this.pageSize = pageSize;
    }

    /**
     * @param pageQuery page
     * @param total total
     */
    private Page(PageQuery<T> pageQuery, Long total) {
        this.content = null;
        this.command = pageQuery;
        this.total = total;
    }

    /**
     * @return return the current page loaded if it wasn't, else return the next
     *         page
     * @throws SQLException page couldn't be loaded
     */
    public Page<T> next() throws SQLException {
        if (!isLoaded()) {
            return this.load();
        }

        Page<T> p = new Page<T>(command, total, this, nbPage + 1);
        return p.load();
    }

    /**
     * @return current page with loaded content
     * @throws SQLException PageException page couldn't be loaded
     */
    public Page<T> load() throws SQLException {
        content = command.getContent(this);
        return this;
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
        return !isLoaded() || (nbPage * pageSize < total);
    }

    public Long getTotalCount() {
        return total;
    }

    public Boolean isLoaded() {
        return content != null;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public Long getCurrentPage() {
        return nbPage;
    }

    /**
     * @return the number of page that can be loaded with pageSize element per page
     *         (with imcomplete page)
     */
    public Long getTotalPages() {
        return PageUtils.getLimit(this.pageSize, this.total);
    }

    public String getSearch() {
        return search;
    }

    public String getFormSort() {
        return columnSort != null ? columnSort.getFormName() : null;
    }

    public String getDbSort() {
        return columnSort != null ? columnSort.getDbName() : null;
    }

    private ComputerMapping getColumnSort() {
        return columnSort;
    }

    public String getOrder() {
        return order;
    }

}
