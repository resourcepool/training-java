package mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Computer;
import model.ComputerPreview;

public class ComputerMapper implements ResultMapper<List<ComputerPreview>>{

	private static final String COMPANY_ID_FIELD = "company_id";
	private static final String DISCONTINUED_FIELD = "discontinued";
	private static final String INTRODUCED_FIELD = "introduced";
	private static final String NAME_FIELD = "name";
	private static final String ID_FIELD = "id";

	public List<ComputerPreview> process(ResultSet rs) throws SQLException {
		List<ComputerPreview> list = new ArrayList<>();
		
		while (rs.next())
		{
			String name = rs.getString(NAME_FIELD);
			String id = rs.getString(ID_FIELD);
			list.add(new ComputerPreview(id, name));
		}
		return list;
	}

	public Computer MapComputer(ResultSet rs) throws SQLException {
		
		if (!rs.next())
		{
			return null;
		}
		
		Long id = rs.getLong(ID_FIELD);
		String name = rs.getString(NAME_FIELD);
		LocalDate introduced = rs.getDate(INTRODUCED_FIELD).toLocalDate();
		Date discontinuedDate = rs.getDate(DISCONTINUED_FIELD);
		LocalDate discontinued = discontinuedDate != null ? discontinuedDate.toLocalDate() : null;
		Long idCompany = rs.getLong(COMPANY_ID_FIELD);
		
		return new Computer(id, name, introduced, discontinued, idCompany);
	}
}
