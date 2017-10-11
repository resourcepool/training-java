package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Company;

public class CompanyMapper implements ResultMapper<List<Company>> {
    /**
     * @param rs companySet loaded from DB
     * @return the model list
     * @throws SQLException an unexpected error occur while accessing datas
     */
    @Override
    public List<Company> process(ResultSet rs) throws SQLException {
        List<Company> list = new ArrayList<>();

        while (rs.next()) {
            String name = rs.getString("name");
            Long id = rs.getLong("id");
            list.add(new Company(id, name));
        }
        return list;
    }
}
