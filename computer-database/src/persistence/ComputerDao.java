package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mapper.ResultToCompanyModels;
import mapper.ResultToComputersPreview;
import model.CompanyModel;
import model.ComputerModel;
import model.ComputerModelPreview;
import persistence.exceptions.DaoException;

public class ComputerDao {
	
	private String url;
	private String password;
	private String user;
	
	//TODO Singleton DAO x2
	public ComputerDao() throws DaoException {
		String database = DbProperties.getConfig("database");
		String port = DbProperties.getConfig("port");
		String hostAddress = DbProperties.getConfig("hostAddress");
		
		url = String.format("jdbc:mysql://%s:%s/%s", hostAddress, port, database);
		password = DbProperties.getConfig("dbpassword");
		user = DbProperties.getConfig("dbuser");
	}
	
	public <T> T executeQuery(QueryCommands<T> p) throws SQLException
	{
		Connection conn = null;
		
		try 
		{
		    conn = DriverManager.getConnection( url, user, password );
		    Statement query = conn.createStatement();
			return p.execute(query);		    
		} 
		catch ( SQLException e ) 
		{
		    throw e;
		} 
		finally 
		{
		    if ( conn != null )
		    {
		    	try {
		            conn.close();
		        } catch (SQLException ignore ) { }
		    }
		}
	}
	
	public List<ComputerModelPreview> getComputersList() throws SQLException 
	{
		List<ComputerModelPreview> result = executeQuery(
			new QueryCommands<List<ComputerModelPreview>>() 
			{
				@Override
				public List<ComputerModelPreview> execute(Statement s) throws SQLException {
					ResultSet set = s.executeQuery("select id, name from computer");
					return ResultToComputersPreview.Process(set);				
				}
			});
		return result;
	}

	public List<CompanyModel> getCompaniesList() throws SQLException 
	{
		List<CompanyModel> result = executeQuery(
			new QueryCommands<List<CompanyModel>>() 
			{
				@Override
				public List<CompanyModel> execute(Statement s) throws SQLException {
					ResultSet set = s.executeQuery("select id, name from company");
					return ResultToCompanyModels.Process(set);				
				}
			});
		return result;
	}

	public long createComputer(ComputerModel newComputer) 
	{
//		boolean result = executeQuery(
//				new QueryCommands<boolean>() 
//				{
//					@Override
//					public boolean execute(Statement s) throws SQLException {
//						s.
//						s.executeQuery()
//						ResultSet set = s.executeQuery("select id, name from computer");
//						return ResultToComputersPreview.Process(set);				
//					}
//				});
		return 1l; //TODO
	}
}
