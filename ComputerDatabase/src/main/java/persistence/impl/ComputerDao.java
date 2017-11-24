package persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import mapper.ComputerMapper;
import model.Computer;
import model.pages.Page;
import persistence.IComputerDao;
import service.PageUtils;

@Repository
public class ComputerDao implements IComputerDao {

    private static final String COUNT_FROM_COMPUTER                   = "select count(*) from computer";
    private static final String COUNT_FROM_COMPUTER_WITH_COMPANY      = "select count(*) from computer CO left join company CA on CA.Id = CO.company_id";
    private static final String SELECT_FROM_COMPUTER_WITH_COMPANY     = "select CO.*, CA.name as company_name from computer CO left join company CA on CA.Id = CO.company_id";
    private static final String INSERT_INTO_COMPUTER_VALUES           = "insert into computer values (null, ?, ?, ?, ?)";
    private static final String UPDATE_COMPUTER                       = "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?";
    private static final String DELETE_FROM_COMPUTER_WHERE_COMPANY_ID = "delete from computer where company_id = ?";
    private static final String DELETE_FROM_COMPUTER_WHERE_ID         = "delete from computer where id = ?";
    private static final String ID_FILTER                             = " where CO.id = ?";
    private static final String SEARCH_FILTER                         = " where lower(CO.name) like ? or lower(CA.name) like ?";

    private JdbcTemplate        jdbc;

    /**
     * @param ds Datasource
     */
    @Autowired
    public ComputerDao(DataSource ds) {
        this.jdbc = new JdbcTemplate(ds);
    }

    // ########################## SELECT, GETTERS #################

    /**
     * @return total number of computer in DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public Long getComputerTotalCount() {
        return jdbc.queryForObject(COUNT_FROM_COMPUTER, Long.class);
    }

    /**
     * @param search filter to used
     * @return total number of computer in DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public Long getComputerTotalCount(String search) {
        String toSearch = '%' + search + '%';
        return jdbc.queryForObject(COUNT_FROM_COMPUTER_WITH_COMPANY + SEARCH_FILTER, Long.class, toSearch, toSearch);
    }

    /**
     * @param id the computer id to search
     * @return the first computer corresponding exactly to @id
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public Computer getComputerDetail(Long id) {
        return jdbc.queryForObject(SELECT_FROM_COMPUTER_WITH_COMPANY + ID_FILTER, (ResultSet rs, int i) -> {
            return new ComputerMapper().mapRow(rs, 0);
        }, id);
    }
    // ########################## PAGES, SEARCH, SORT, LIMIT #######################

    /**
     * @param page number of total elements than can be loaded
     * @return the content of one computer page from DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public List<Computer> get(Page<Computer> page) {

        StringBuilder sb = new StringBuilder(SELECT_FROM_COMPUTER_WITH_COMPANY);
        List<Object> parameters = new ArrayList<Object>();
        Long start = PageUtils.getFirstEntityIndex(page);

        if (page.getSearch() != null) {
            sb.append(SEARCH_FILTER);
            parameters.add('%' + page.getSearch() + '%');
            parameters.add('%' + page.getSearch() + '%');
        }

        if (page.getDbSort() != null) {
            sb.append(String.format(" ORDER BY %s %s", page.getDbSort(), page.getOrder()));
        } else {
            sb.append(" ORDER BY CO.id");
        }

        sb.append(" LIMIT ? OFFSET ?");
        parameters.add(page.getPageSize());
        parameters.add(start);

        return jdbc.query(sb.toString(), parameters.toArray(), new ComputerMapper());
    }

    // ########################## CREATE, UPDATE and DELETE #################

    /**
     * @param newComputer complete computer to create, without id
     * @return the id of the created computer
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public Long createComputer(Computer newComputer) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection c) throws SQLException {
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
                return s;
            }
        }, holder);

        return holder.getKey().longValue();
    }

    /**
     * @param c full computer to update with id != null
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public void updateComputer(Computer c) {
        jdbc.update((Connection conn) -> {
            PreparedStatement s = conn.prepareStatement(UPDATE_COMPUTER);
            s.setString(1, c.getName());
            s.setDate(2, c.getIntroduced() == null ? null : Date.valueOf(c.getIntroduced()));
            s.setDate(3, c.getDiscontinued() == null ? null : Date.valueOf(c.getDiscontinued()));

            if (c.getCompany().getId() != null) {
                s.setLong(4, c.getCompany().getId());
            } else {
                s.setNull(4, Types.BIGINT);
            }

            s.setLong(5, c.getId());
            return s;
        });
    }

    /**
     * @param id id of the computer to delete
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public void deleteComputer(Long id) {
        jdbc.update(DELETE_FROM_COMPUTER_WHERE_ID, id);
    }

    /**
     * @param id id of the computer to delete
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public void deleteByCompany(Long id) {
        jdbc.update(DELETE_FROM_COMPUTER_WHERE_COMPANY_ID, id);
    }

    /**
     * @param ids ids to delete
     * @throws SQLException failed
     */
    @Override
    public void deleteComputers(List<Long> ids) {
        String filter = ids.stream().map(number -> String.valueOf(number)).collect(Collectors.joining(","));
        jdbc.update("delete from computer where id in (" + filter + ")");
    }

}
