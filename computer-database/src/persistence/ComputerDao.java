package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import mapper.ComputerMapper;
import mapper.pages.Page;
import mapper.pages.Page;
import model.Computer;
import model.ComputerPreview;
import persistence.querycommands.PageQueryCommand;

public class ComputerDao {
	
	private static final String SELECT_COUNT_FROM_COMPUTER = "select count(*) from computer";
	private static final String SELECT_ID_NAME_FROM_COMPUTER = "select id, name from computer";
	private static final String SELECT_COMPUTER_WHERE = "select * from computer where ";
	private static final String INSERT_INTO_COMPUTER_VALUES = "insert into computer values (null, ?, ?, ?, ?)";
	
	private static final String ID_FILTER = "id = ?";
	private static final String NAME_FILTER = "name = \"?\"";
	
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
				new ComputerMapper());
	}
	
	public Page<ComputerPreview> getComputerPage() throws SQLException
	{
		Long size = DaoConnection.executeSelectQuery(SELECT_COUNT_FROM_COMPUTER, 
			(ResultSet r) -> {
				return (r.next() ? r.getLong(1) : null);
			});
		
		PageQueryCommand<ComputerPreview> command = 
			(Long start, Long splitSize) ->  
			{
				return getComputerPageContent(start, splitSize);
			};
			
		return new Page<ComputerPreview>(command , size);
	}
	
	private List<ComputerPreview> getComputerPageContent(Long start, Long split) throws SQLException
	{
		String filter = String.format(" ORDER BY id LIMIT %d,%d", start, split);
		return DaoConnection.executeSelectQuery(
				SELECT_ID_NAME_FROM_COMPUTER + filter, 
				new ComputerMapper());
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
			ResultSet r = s.getGeneratedKeys();
			Long id = r.next() ? r.getLong(1) : null;
			
			r.close();
			s.close();
			return id;		
		});
	}

	public Computer getComputerDetail(String name) throws SQLException {
		return getComputerDetail(NAME_FILTER, name);
	}
	
	public Computer getComputerDetail(Long id) throws SQLException 
	{
		return getComputerDetail(ID_FILTER, id.toString());
	}

	private Computer getComputerDetail(String queryFilter, String param) throws SQLException {
		return DaoConnection.executeQuery((Connection conn) -> 
		{
			PreparedStatement s = conn.prepareStatement(SELECT_COMPUTER_WHERE + queryFilter);
			s.setString(1, param);
			ResultSet r = s.executeQuery();
			
			Computer c = new ComputerMapper().MapComputer(r);
			
			r.close();
			s.close();
			return c;
		});
	}
	
	public void updateComputer(Computer c) throws SQLException {
		DaoConnection.executeQuery((Connection conn) -> 
		{
			PreparedStatement s = conn.prepareStatement("update computer set name = ?, introduced = ? discontinued = ?, company_id = ?");
			s.setString(1, c.getName());
			s.setDate(2, Date.valueOf(c.getIntroduced()));
			s.setDate(3, c.getDiscontinued() == null ? null : Date.valueOf(c.getDiscontinued()));
			s.setLong(4, c.getCompanyId());
			s.executeUpdate();
			
			s.close();
			return true;
		});
	}
	public void deleteComputer(Long id) throws SQLException {
		DaoConnection.executeQuery((Connection conn) -> 
		{
			try (Statement s = conn.createStatement())
			{
				s.execute("delete from computer where id = " + id);	
			}
			return true;
		});
	}
}
