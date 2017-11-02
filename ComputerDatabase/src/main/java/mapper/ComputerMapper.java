package mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Company;
import model.Computer;

public class ComputerMapper implements ResultMapper<List<Computer>> {

    public static final String  COMPANY_NAME_FIELD = "company_name";
    private static final String COMPANY_ID_FIELD   = "company_id";
    private static final String DISCONTINUED_FIELD = "discontinued";
    private static final String INTRODUCED_FIELD   = "introduced";
    private static final String NAME_FIELD         = "name";
    private static final String ID_FIELD           = "id";

    /**
     * @param rs ComputerPreview Set loaded from DB
     * @return the model list
     * @throws SQLException an unexpected error occur while accessing datas
     */
    @Override
    public List<Computer> process(ResultSet rs) throws SQLException {
        List<Computer> list = new ArrayList<>();

        while (rs.next()) {
            list.add(mapOneComputer(rs));
        }
        return list;
    }

    /**
     * @param rs one computer loaded from DB
     * @return the computer details
     * @throws SQLException an unexpected error occur while accessing datas
     */
    public Computer mapComputer(ResultSet rs) throws SQLException {

        if (!rs.next()) {
            return null;
        }

        return mapOneComputer(rs);
    }

    /**
     * @param rs Already loaded ResultSet (next() have already been called)
     * @return a computer with each fields filled
     * @throws SQLException One value couldn't be loaded
     */
    private Computer mapOneComputer(ResultSet rs) throws SQLException {
        Long id = rs.getLong(ID_FIELD);
        String name = rs.getString(NAME_FIELD);
        Date introducedDate = rs.getDate(INTRODUCED_FIELD);
        LocalDate introduced = introducedDate != null ? introducedDate.toLocalDate() : null;
        Date discontinuedDate = rs.getDate(DISCONTINUED_FIELD);
        LocalDate discontinued = discontinuedDate != null ? discontinuedDate.toLocalDate() : null;

        Long idCompany = rs.getLong(COMPANY_ID_FIELD);
        String nameCompany = rs.getString(COMPANY_NAME_FIELD);
        Company company = new Company(idCompany == 0 ? null : idCompany, nameCompany);

        return new Computer(id, name, introduced, discontinued, company);
    }


}
