package persistence;

import java.sql.SQLException;
import java.util.List;

import mapper.ResultToCompanyModels;
import model.Company;

public class CompanyDao {
	private static CompanyDao INSTANCE;
	
	private CompanyDao() { }
	
	public static CompanyDao getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new CompanyDao();
		}
		return INSTANCE;
	}
	
	public List<Company> getCompanyList() throws SQLException 
	{
		return DaoConnection.executeSelectQuery(
				"select id, name from company", 
				new ResultToCompanyModels());
	}
}
