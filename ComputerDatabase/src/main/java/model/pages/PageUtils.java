package model.pages;

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
    public static Long getStartElem(Page<?> page) {
        return (page.getCurrentPage() - 1) * page.getPageSize();
    }
}
