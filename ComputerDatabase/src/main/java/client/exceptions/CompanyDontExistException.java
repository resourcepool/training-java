package client.exceptions;

public class CompanyDontExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * @param idCompany company tested that failed
     */
    public CompanyDontExistException(Long idCompany) {
        super(String.format("Company %d does not exist !", idCompany));
    }

}
