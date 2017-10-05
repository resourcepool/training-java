package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import mapper.ResultToCompany;
import mapper.ResultToComputer;
import model.Company;
import model.Computer;

public class CompanyDao {
	private static final String SELECT_ID_NAME_FROM_COMPANY = "select id, name from company";
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
				SELECT_ID_NAME_FROM_COMPANY, 
				new ResultToCompany());
	}

	public boolean companyExists(Long idCompany) throws SQLException {
		return DaoConnection.executeQuery((Connection conn) -> 
		{
			PreparedStatement s = conn.prepareStatement("select count(*) from company where id = ?");
			s.setLong(1, idCompany);
			
			ResultSet r = s.executeQuery();
			int count = r.next() ? r.getInt(1) : -1;
			
			s.close();
			return count > 0;		
		});
	}
}
