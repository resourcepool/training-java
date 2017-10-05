package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import mapper.ResultToComputersPreview;
import model.Computer;
import model.ComputerPreview;

public class ComputerDao {
	
	private static final String SELECT_ID_NAME_FROM_COMPUTER = "select id, name from computer";
	private static final String INSERT_INTO_COMPUTER_VALUES = "insert into computer values (?, ?, ?, ?)";
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
				new ResultToComputersPreview());
	}

	public void createComputer(Computer newComputer) throws SQLException 
	{
		DaoConnection.executeQuery((Connection c) ->
		{
			PreparedStatement s = c.prepareStatement(INSERT_INTO_COMPUTER_VALUES);
			
			s.setString(1, newComputer.getName());
			s.setDate(2, Date.valueOf(newComputer.getIntroduced()));
			s.setDate(3, Date.valueOf(newComputer.getDiscontinued()));
			s.setLong(4, newComputer.getCompanyId());
			
			s.execute();
			return true;		
		});
	}
}
