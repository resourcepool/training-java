package mapper.exceptions;

public class PageException extends Exception {

    private static final long serialVersionUID = 2391270800603319889L;

    /**
     * @param e reason the page failed to load
     */
    public PageException(Exception e) {
        super(e);
    }

}
