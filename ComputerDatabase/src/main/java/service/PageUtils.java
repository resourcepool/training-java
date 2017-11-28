package service;

import model.pages.Page;

public class PageUtils {

    /**
     * @param page loaded page
     * @return concatenation of each .toString() loaded content
     */
    public static String dump(Page<?> page) {
        StringBuilder b = new StringBuilder();
        for (Object t : page.getContent()) {
            b.append(t.toString());
            b.append(System.lineSeparator());
        }
        return b.toString();
    }

    /**
     * @param page current page (not need to be loaded)
     * @return the start index of the db entity of the given page
     */
    public static Long getFirstEntityIndex(Page<?> page) {
        return (getFirstEntityIndex(page.getCurrentPage(), page.getPageSize()));
    }

    /**
     * @param pageNumber current page nb
     * @param pageSize size of a page
     * @return the start index of the db entity of the given page
     */
    public static Long getFirstEntityIndex(Long pageNumber, Long pageSize) {
        return (pageNumber - 1) * pageSize;
    }

    /**
     * @param pageSize pageSize
     * @param total total of elem/entities count
     * @return return the last possible page
     */
    public static Long getLimit(Long pageSize, Long total) {
        return (total + pageSize - 1) / pageSize;
    }
}
