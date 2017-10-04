package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ComputerModelPreview;

public class ResultToComputersPreview {

	public static ArrayList<ComputerModelPreview> Process(ResultSet rs) throws SQLException {
		ArrayList<ComputerModelPreview> list = new ArrayList<>();
		
		while (rs.next())
		{
			String name = rs.getString("name");
			long id = rs.getLong("id");
			list.add(new ComputerModelPreview(id, name));
		}
		return list;
	}

}
