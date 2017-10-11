package client.exceptions;

public class ClientDataFormatException extends RuntimeException {

    /**
     * @param string the error msg
     */
    public ClientDataFormatException(String string) {
        super(string);
    }

    private static final long serialVersionUID = 1L;

}
