package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

import mapper.ComputerMapper;
import model.Computer;
import persistence.exceptions.DaoException;

public class ComputerDaoImpl {

    private static final String SELECT_COUNT_FROM_COMPUTER = "select count(*) from computer CO";
    private static final String SELECT_FROM_COMPUTER_WITH_COMPANY = "select CO.*, CA.name as company_name from computer CO left join company CA on CA.Id = CO.company_id";
    private static final String INSERT_INTO_COMPUTER_VALUES = "insert into computer values (null, ?, ?, ?, ?)";
    private static final String ID_FILTER = " where CO.id = ?";
    private static final String NAME_FILTER = " where CO.name = \"?\"";

    // ######################### SINGLETON ###################################
    private static ComputerDaoImpl instance;

    /**
     * private ctor.
     */
    private ComputerDaoImpl() {
    }

    /**
     * @return unique instance of this dao
     */
    public static ComputerDaoImpl getInstance() {
        if (instance == null) {
            instance = new ComputerDaoImpl();
        }
        return instance;
    }

    // ########################## SELECT, GETTERS #################

    /**
     * @return total number of computer in DB
     * @throws DaoException content couldn't be loaded
     */
    public Long getComputerTotalCount() throws DaoException {
        Long size = DaoConnection.executeSelectQuery(SELECT_COUNT_FROM_COMPUTER, (ResultSet r) -> {
            return (r.next() ? r.getLong(1) : null);
        });
        return size;
    }

    /**
     * @param name the name to search
     * @return the first computer corresponding exactly to @name
     * @throws DaoException content couldn't be loaded
     */
    public Computer getComputerDetail(String name) throws DaoException {
        return getComputerDetail(NAME_FILTER, name);
    }

    /**
     * @param id the computer id to search
     * @return the first computer corresponding exactly to @id
     * @throws DaoException content couldn't be loaded
     */
    public Computer getComputerDetail(Long id) throws DaoException {
        return getComputerDetail(ID_FILTER, id.toString());
    }

    /**
     * @param queryFilter the String containing the filter
     * @param param the string connection the param to fill the @queryfilter with,
     *            escaping the content in a preparedStatement
     * @return the first computer corresponding to filter
     * @throws DaoException content couldn't be loaded
     */
    private Computer getComputerDetail(String queryFilter, String param) throws DaoException {
        return DaoConnection.executeQuery((Connection conn) -> {
            PreparedStatement s = conn.prepareStatement(SELECT_FROM_COMPUTER_WITH_COMPANY + queryFilter);
            s.setString(1, param);
            ResultSet r = s.executeQuery();

            Computer c = new ComputerMapper().mapComputer(r);

            r.close();
            s.close();
            return c;
        });
    }

    // ########################## PAGES, SEARCH, SORT, LIMIT #######################

    /**
     * @param start element index to start
     * @param splitSize number of total elements than can be loaded
     * @return the content of one computer page from DB
     * @throws DaoException content couldn't be loaded
     */
    public List<Computer> getContent(Long start, Long splitSize) throws DaoException {

        String filter = String.format(" ORDER BY name LIMIT %d,%d", start, splitSize); // where CO.name = 'zzzzz'
        String sql = SELECT_FROM_COMPUTER_WITH_COMPANY + filter;
        return DaoConnection.executeSelectQuery(sql, new ComputerMapper());
    }

    /**
     * @param start element index to start
     * @param splitSize number of total elements than can be loaded
     * @param search companyName or computerName to search
     * @return the content of one computer page from DB
     * @throws DaoException content couldn't be loaded
     */
    public List<Computer> getContent(Long start, Long splitSize, String search) throws DaoException {

        String where = "where CO.name like '%" + search + "%' or CA.name like '%" + search + "%'";
        String filter = String.format("%s ORDER BY name LIMIT %d,%d", where, start, splitSize);
        String sql = SELECT_FROM_COMPUTER_WITH_COMPANY + filter;
        return DaoConnection.executeSelectQuery(sql, new ComputerMapper());
    }

    // ########################## CREATE, UPDATE and DELETE #################

    /**
     * @param newComputer complete computer to create, without id
     * @return the id of the created computer
     * @throws DaoException content couldn't be loaded
     */
    public Long createComputer(Computer newComputer) throws DaoException {
        return DaoConnection.executeQuery((Connection c) -> {
            PreparedStatement s = c.prepareStatement(INSERT_INTO_COMPUTER_VALUES, Statement.RETURN_GENERATED_KEYS);

            s.setString(1, newComputer.getName());
            s.setDate(2, newComputer.getIntroduced() == null ? null : Date.valueOf(newComputer.getIntroduced()));
            s.setDate(3, newComputer.getDiscontinued() == null ? null : Date.valueOf(newComputer.getDiscontinued()));

            Long companyId = newComputer.getCompany().getId();
            if (companyId != null) {
                s.setLong(4, companyId);
            } else {
                s.setNull(4, Types.BIGINT);
            }

            s.executeUpdate();
            ResultSet r = s.getGeneratedKeys();
            Long id = r.next() ? r.getLong(1) : null;

            r.close();
            s.close();
            return id;
        });
    }

    /**
     * @param c full computer to update with id != null
     * @throws DaoException content couldn't be loaded
     */
    public void updateComputer(Computer c) throws DaoException {
        DaoConnection.executeQuery((Connection conn) -> {
            PreparedStatement s = conn.prepareStatement(
                    "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?");
            s.setString(1, c.getName());
            s.setDate(2, Date.valueOf(c.getIntroduced()));
            s.setDate(3, c.getDiscontinued() == null ? null : Date.valueOf(c.getDiscontinued()));

            if (c.getCompany().getId() != null) {
                s.setLong(4, c.getCompany().getId());
            } else {
                s.setNull(4, Types.BIGINT);
            }

            s.setLong(5, c.getId());
            s.executeUpdate();

            s.close();
            return true;
        });
    }

    /**
     * @param id id of the computer to delete
     * @throws DaoException content couldn't be loaded
     */
    public void deleteComputer(Long id) throws DaoException {
        DaoConnection.executeQuery((Connection conn) -> {
            try (Statement s = conn.createStatement()) {
                s.execute("delete from computer where id = " + id);
            }
            return true;
        });
    }

    /**
     * @param ids ids to delete
     * @throws DaoException failed
     */
    public void deleteComputers(List<Long> ids) throws DaoException {
        String filter = ids.stream().map(number -> String.valueOf(number)).collect(Collectors.joining(","));

        DaoConnection.executeQuery((Connection conn) -> {
            try (Statement s = conn.createStatement()) {
                s.execute("delete from computer where id in (" + filter + ")");
            }
            return true;
        });
    }

}
