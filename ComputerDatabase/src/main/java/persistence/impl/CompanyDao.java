package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mapper.CompanyMapper;
import model.Company;
import model.pages.Page;
import persistence.DaoConnection;
import persistence.ICompanyDao;
import persistence.querycommands.PageQuery;
import service.PageUtils;

@Repository
public class CompanyDao implements ICompanyDao {

    private static final String DELETE_FROM_COMPANY_WHERE_ID = "delete from company where id = ?";
    private static final String SELECT_COUNT_FROM_COMPANY = "select count(*) from company";
    private static final String SELECT_COUNT_FROM_COMPANY_WHERE_ID = "select count(*) from company where id = ?";
    private static final String SELECT_ID_NAME_FROM_COMPANY = "select id, name from company order by name";

    private DaoConnection conn;

    /**
     * @param conn Dao Connection Manager
     */
    @Autowired
    public CompanyDao(DaoConnection conn) {
        this.conn = conn;
    }

    /**
     * @return Full company list from DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public List<Company> getCompanyList() throws SQLException {
        return conn.executeSelectQuery(SELECT_ID_NAME_FROM_COMPANY, new CompanyMapper());
    }

    /**
     * @param idCompany the id to check
     * @return true is company id is present in DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public boolean companyExists(Long idCompany) throws SQLException {
        return conn.executeQuery((Connection conn) -> {
            try (PreparedStatement s = conn.prepareStatement(SELECT_COUNT_FROM_COMPANY_WHERE_ID)) {
                s.setLong(1, idCompany);

                try (ResultSet r = s.executeQuery()) {
                    return (r.next() ? r.getLong(1) > 0 : false);
                }
            }
        });
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
            String filter = String.format(" ORDER BY id LIMIT %d,%d", startElem, p.getPageSize());
            return conn.executeSelectQuery(SELECT_ID_NAME_FROM_COMPANY + filter, new CompanyMapper());
        };

        return new Page<Company>(command, size, 10L, 1L);
    }

    /**
     * @return the number of company in DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public Long getCompanyCount() throws SQLException {
        Long size = conn.executeSelectQuery(SELECT_COUNT_FROM_COMPANY, (ResultSet r) -> {
            return (r.next() ? r.getLong(1) : null);
        });
        return size;
    }

    /**
     * @param id id to delete
     * @throws SQLException failed to delete
     */
    @Override
    public void deleteCompany(Long id) throws SQLException {

        conn.executeQuery((Connection conn) -> {

            PreparedStatement deleteCompany = null;

            try {
                deleteCompany = conn.prepareStatement(DELETE_FROM_COMPANY_WHERE_ID);
                deleteCompany.setLong(1, id);

                deleteCompany.executeUpdate();

            } finally {

                if (deleteCompany != null) {
                    deleteCompany.close();
                }
            }

            return true;
        });

    }
}
