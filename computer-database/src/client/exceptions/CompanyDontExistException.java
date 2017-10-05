package client.exceptions;

public class CompanyDontExistException extends Exception {

	public CompanyDontExistException(Long idCompany) {
		super(String.format("Company %d does not exist !", idCompany));
	}
	
}
