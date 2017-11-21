package persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

import mapper.ComputerMapper;
import model.Computer;
import model.pages.Page;
import persistence.DaoConnection;
import persistence.IComputerDao;
import service.PageUtils;

public class ComputerDao implements IComputerDao {

    private static final String SEARCH_FILTER                         = "where lower(CO.name) like '%%%1$s%%' or lower(CA.name) like '%%%1$s%%'";
    private static final String COUNT_FROM_COMPUTER                   = "select count(*) from computer";
    private static final String COUNT_FROM_COMPUTER_WITH_COMPANY      = "select count(*) from computer CO left join company CA on CA.Id = CO.company_id";
    private static final String SELECT_FROM_COMPUTER_WITH_COMPANY     = "select CO.*, CA.name as company_name from computer CO left join company CA on CA.Id = CO.company_id";
    private static final String INSERT_INTO_COMPUTER_VALUES           = "insert into computer values (null, ?, ?, ?, ?)";
    private static final String ID_FILTER                             = " where CO.id = ?";
    private static final String DELETE_FROM_COMPUTER_WHERE_COMPANY_ID = "delete from computer where company_id = ?";

    private DaoConnection       conn;

    /**
     * @param conn Dao Connection Manager
     */
    public ComputerDao(DaoConnection conn) {
        this.conn = conn;
    }

    // ########################## SELECT, GETTERS #################

    /**
     * @return total number of computer in DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public Long getComputerTotalCount() throws SQLException {
        return conn.getCount(COUNT_FROM_COMPUTER);
    }

    /**
     * @param search filter to used
     * @return total number of computer in DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public Long getComputerTotalCount(String search) throws SQLException {
        String req = COUNT_FROM_COMPUTER_WITH_COMPANY + ' ' + String.format(SEARCH_FILTER, search);
        return conn.getCount(req);
    }

    /**
     * @param id the computer id to search
     * @return the first computer corresponding exactly to @id
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public Computer getComputerDetail(Long id) throws SQLException {
        return conn.executeQuery((Connection conn) -> {
            PreparedStatement s = conn.prepareStatement(SELECT_FROM_COMPUTER_WITH_COMPANY + ID_FILTER);
            s.setString(1, id.toString());
            ResultSet r = s.executeQuery();

            Computer c = new ComputerMapper().mapComputer(r);

            r.close();
            s.close();
            return c;
        });
    }
    // ########################## PAGES, SEARCH, SORT, LIMIT #######################

    /**
     * @param page number of total elements than can be loaded
     * @return the content of one computer page from DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public List<Computer> get(Page<Computer> page) throws SQLException {
        // TODO PrepareStatement
        Long start = PageUtils.getFirstEntityIndex(page);
        StringBuilder sb = new StringBuilder(SELECT_FROM_COMPUTER_WITH_COMPANY);
        sb.append(' ');

        if (page.getSearch() != null) {
            sb.append(String.format(SEARCH_FILTER, page.getSearch()));
            sb.append(' ');
        }

        if (page.getDbSort() != null) {
            sb.append(String.format("ORDER BY %s %s", page.getDbSort(), page.getOrder()));
        } else {
            sb.append("ORDER BY CO.id");
        }
        sb.append(' ');

        sb.append(String.format(" LIMIT %d,%d", start, page.getPageSize()));

        return conn.executeSelectQuery(sb.toString(), new ComputerMapper());
    }

    // ########################## CREATE, UPDATE and DELETE #################

    /**
     * @param newComputer complete computer to create, without id
     * @return the id of the created computer
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public Long createComputer(Computer newComputer) throws SQLException {
        return conn.executeQuery((Connection c) -> {
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
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public void updateComputer(Computer c) throws SQLException {
        conn.executeQuery((Connection conn) -> {
            PreparedStatement s = conn.prepareStatement(
                    "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?");
            s.setString(1, c.getName());
            s.setDate(2, c.getIntroduced() == null ? null : Date.valueOf(c.getIntroduced()));
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
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public void deleteComputer(Long id) throws SQLException {
        conn.executeQuery((Connection conn) -> {
            try (Statement s = conn.createStatement()) {
                s.executeUpdate("delete from computer where id = " + id);
            }
            return true;
        });
    }

    /**
     * @param id id of the computer to delete
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public void deleteByCompany(Long id) throws SQLException {
        conn.executeQuery((Connection conn) -> {
            try (PreparedStatement s = conn.prepareStatement(DELETE_FROM_COMPUTER_WHERE_COMPANY_ID)) {
                s.setObject(1, id);
                s.executeUpdate();
            }
            return true;
        });
    }

    /**
     * @param ids ids to delete
     * @throws SQLException failed
     */
    @Override
    public void deleteComputers(List<Long> ids) throws SQLException {

        String filter = ids.stream().map(number -> String.valueOf(number)).collect(Collectors.joining(","));

        conn.executeQuery((Connection conn) -> {
            try (Statement s = conn.createStatement()) {
                s.executeUpdate("delete from computer where id in (" + filter + ")");
            }
            return true;
        });
    }

}
