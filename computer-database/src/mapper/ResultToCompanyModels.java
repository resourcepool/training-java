package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.CompanyModel;

public class ResultToCompanyModels {
	public static ArrayList<CompanyModel> Process(ResultSet rs) throws SQLException {
		ArrayList<CompanyModel> list = new ArrayList<>();
		
		while (rs.next())
		{
			String name = rs.getString("name");
			Long id = rs.getLong("id");
			list.add(new CompanyModel(id, name));
		}
		return list;
	}
}
