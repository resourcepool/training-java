package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ComputerPreview;

public class ResultToComputersPreview implements ResultMapper<List<ComputerPreview>>{

	public List<ComputerPreview> process(ResultSet rs) throws SQLException {
		List<ComputerPreview> list = new ArrayList<>();
		
		while (rs.next())
		{
			String name = rs.getString("name");
			String id = rs.getString("id");
			list.add(new ComputerPreview(id, name));
		}
		return list;
	}

}
