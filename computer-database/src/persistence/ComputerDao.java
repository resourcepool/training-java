package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import mapper.ResultToComputer;
import model.Computer;
import model.ComputerPreview;

public class ComputerDao {
	
	private static final String SELECT_COMPUTER_WHERE_ID = "select * from computer where id = ?";
	private static final String SELECT_ID_NAME_FROM_COMPUTER = "select id, name from computer";
	private static final String INSERT_INTO_COMPUTER_VALUES = "insert into computer values (null, ?, ?, ?, ?)";
	private static ComputerDao INSTANCE;
	
	private ComputerDao() { }
	
	public static ComputerDao getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new ComputerDao();
		}
		return INSTANCE;
	}
	
	public List<ComputerPreview> getComputersList() throws SQLException 
	{
		return DaoConnection.executeSelectQuery(
				SELECT_ID_NAME_FROM_COMPUTER, 
				new ResultToComputer());
	}
	
	public Computer getComputerDetail(Long id) throws SQLException 
	{
		return DaoConnection.executeQuery((Connection conn) -> 
		{
			PreparedStatement s = conn.prepareStatement(SELECT_COMPUTER_WHERE_ID);
			s.setLong(1, id);
			
			ResultSet r = s.executeQuery();
			Computer c = new ResultToComputer().MapComputer(r);
			
			s.close();
			return c;
		});
			
	}

	public Long createComputer(Computer newComputer) throws SQLException 
	{
		return DaoConnection.executeQuery((Connection c) ->
		{
			PreparedStatement s = c.prepareStatement(INSERT_INTO_COMPUTER_VALUES, Statement.RETURN_GENERATED_KEYS);
			
			s.setString(1, newComputer.getName());
			s.setDate(2, Date.valueOf(newComputer.getIntroduced()));
			s.setDate(3, newComputer.getDiscontinued() == null ? null : Date.valueOf(newComputer.getDiscontinued()));
			s.setLong(4, newComputer.getCompanyId());

			s.executeUpdate();
			ResultSet keys = s.getGeneratedKeys();
			Long id = keys.next() ? keys.getLong(1) : null;
			
			s.close();
			return id;		
		});
	}

	public Computer getComputerDetail(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
