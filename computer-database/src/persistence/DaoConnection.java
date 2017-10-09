package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mapper.ResultMapper;
import persistence.exceptions.DaoException;

public class DaoConnection {
	private static final String database = DbProperties.getConfig("database");
	private static final String port = DbProperties.getConfig("port");
	private static final String hostAddress = DbProperties.getConfig("hostAddress");
	 
	private static final String url = String.format("jdbc:mysql://%s:%s/%s?autoReconnect=true&useSSL=false", hostAddress, port, database);;
	private static final String password = DbProperties.getConfig("dbpassword");;
	private static final String user = DbProperties.getConfig("dbuser");
	
	private static final Logger logger = LoggerFactory.getLogger(DaoConnection.class);
	
	public static <T> T executeSelectQuery(String sql, ResultMapper<T> m) throws SQLException
	{
		return executeQuery((Connection c) -> {
			 Statement s = c.createStatement();
			 ResultSet r = s.executeQuery(sql);
			 T result = m.process(r);
			 r.close();
			 s.close();
			return result;
		});
	}
	
	public static <T> T executeQuery(QueryCommands<T> q) throws SQLException
	{
		Connection conn = null;
		
		try {
		    conn = DriverManager.getConnection( url, user, password );
		    return q.execute(conn);		    
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		finally 
		{
		    if ( conn != null )
		    {
		    	try {
		            conn.close();
		        } catch (SQLException e ) { 
		        	logger.error("Connection failed to close");
		        }
		    }
		}
	}
	
}
