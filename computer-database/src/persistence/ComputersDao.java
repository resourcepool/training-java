package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mapper.ResultToCompanyModels;
import mapper.ResultToComputersPreview;
import model.CompanyModel;
import model.ComputerModel;
import model.ComputerModelPreview;

public class ComputersDao {
	
	private String hostAddress = "localhost";
	private int port = 3306;
	private String database = "computer-database-db";
	private String url = String.format("jdbc:mysql://%s:%d/%s", hostAddress, port, database);
	private String utilisateur = "admincdb";
	private String motDePasse = "qwerty1234";
	
	public ComputersDao() throws ClassNotFoundException {
		try {
		    Class.forName( "com.mysql.jdbc.Driver" );
		} catch ( ClassNotFoundException e ) {
		    throw e;
		}
	}
	
	public <T> T executeQuery(QueryCommands<T> p) throws SQLException
	{
		Connection conn = null;
		
		try 
		{
		    conn = DriverManager.getConnection( url, utilisateur, motDePasse );
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
	
	public ArrayList<ComputerModelPreview> getComputersList() throws SQLException {
		
		ArrayList<ComputerModelPreview> result = executeQuery(
			new QueryCommands<ArrayList<ComputerModelPreview>>() 
			{
				@Override
				public ArrayList<ComputerModelPreview> execute(Statement s) throws SQLException {
					ResultSet set = s.executeQuery("select id, name from computer");
					return ResultToComputersPreview.Process(set);				
				}
			});
		return result;
	}

	public ArrayList<CompanyModel> getCompaniesList() throws SQLException {
		ArrayList<CompanyModel> result = executeQuery(
				new QueryCommands<ArrayList<CompanyModel>>() 
				{
					@Override
					public ArrayList<CompanyModel> execute(Statement s) throws SQLException {
						ResultSet set = s.executeQuery("select id, name from company");
						return ResultToCompanyModels.Process(set);				
					}
				});
			return result;
	}

	public long createComputer(ComputerModel newComputer) {
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
