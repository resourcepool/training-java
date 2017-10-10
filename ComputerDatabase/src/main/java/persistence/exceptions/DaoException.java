package persistence.exceptions;

public class DaoException extends Error {

	private static final long serialVersionUID = 1L;
	
	public DaoException(Exception e) {
		super(e);
	}
	
	public DaoException(String message)
	{
		super(message);
	}
}
