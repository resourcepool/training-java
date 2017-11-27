package mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Company;
import model.Computer;

public class ComputerMapper implements IResultMapper<List<Computer>> {

    public static final String COMPANY_NAME = "company_name";
    public static final String COMPANY_ID   = "CO.company_id";
    public static final String DISCONTINUED = "CO.discontinued";
    public static final String INTRODUCED   = "CO.introduced";
    public static final String NAME         = "CO.name";
    public static final String ID           = "CO.id";

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
        Long id = rs.getLong(ID);
        String name = rs.getString(NAME);
        Date introducedDate = rs.getDate(INTRODUCED);
        LocalDate introduced = introducedDate != null ? introducedDate.toLocalDate() : null;
        Date discontinuedDate = rs.getDate(DISCONTINUED);
        LocalDate discontinued = discontinuedDate != null ? discontinuedDate.toLocalDate() : null;

        Long idCompany = rs.getLong(COMPANY_ID);
        String nameCompany = rs.getString(COMPANY_NAME);
        Company company = new Company(idCompany == 0 ? null : idCompany, nameCompany);

        return new Computer(id, name, introduced, discontinued, company);
    }

}
