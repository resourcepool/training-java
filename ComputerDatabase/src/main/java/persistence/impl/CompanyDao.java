package persistence.impl;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mapper.CompanyMapper;
import model.Company;
import model.pages.Page;
import persistence.ICompanyDao;
import persistence.querycommands.PageQuery;
import service.PageUtils;

@Repository
public class CompanyDao implements ICompanyDao {

    private static final String DELETE_FROM_COMPANY_WHERE_ID = "delete from company where id = ?";
    private static final String SELECT_COUNT_FROM_COMPANY = "select count(*) from company";
    private static final String SELECT_COUNT_FROM_COMPANY_WHERE_ID = "select count(*) from company where id = ?";
    private static final String SELECT_ID_NAME_FROM_COMPANY = "select id, name from company order by name";

    private CompanyMapper ROW_MAPPER = new CompanyMapper();
    private JdbcTemplate jdbc;

    /**
     * @param ds dataSource
     */
    @Autowired
    public CompanyDao(DataSource ds) {
        this.jdbc = new JdbcTemplate(ds);

    }

    /**
     * @return Full company list from DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public List<Company> getCompanyList() throws SQLException {
        return jdbc.queryForObject(SELECT_ID_NAME_FROM_COMPANY, ROW_MAPPER);
    }

    /**
     * @param idCompany the id to check
     * @return true is company id is present in DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public boolean companyExists(Long idCompany) throws SQLException {
        Long count =  jdbc.queryForObject(SELECT_COUNT_FROM_COMPANY_WHERE_ID, Long.class);
        return count != null && count > 0;
    }

    /**
     * @return the first page of the full company list from DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public Page<Company> getCompanyPage() throws SQLException {
        Long size = getCompanyCount();

        PageQuery<Company> command = (Page<Company> p) -> {
            Long startElem = PageUtils.getFirstEntityIndex(p);
            String filter = " ORDER BY id LIMIT ?, ?";
            return jdbc.queryForList(SELECT_ID_NAME_FROM_COMPANY + filter, Company.class, startElem, p.getPageSize());
        };

        return new Page<Company>(command, size, 10L, 1L);
    }

    /**
     * @return the number of company in DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public Long getCompanyCount() throws SQLException {
        return jdbc.queryForObject(SELECT_COUNT_FROM_COMPANY, Long.class);
    }

    /**
     * @param id id to delete
     * @throws SQLException failed to delete
     */
    @Override
    public void deleteCompany(Long id) throws SQLException {
        jdbc.update(DELETE_FROM_COMPANY_WHERE_ID, id);
    }
}
