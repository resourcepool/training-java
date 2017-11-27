package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import mapper.CompanyMapper;
import model.Company;
import model.pages.Page;
import model.pages.PageUtils;
import persistence.exceptions.DaoException;
import persistence.querycommands.PageQuery;

public class CompanyDaoImpl {
    private static final String DELETE_FROM_COMPANY_WHERE_ID = "delete from company where id = ?";

    private static final String SELECT_COUNT_FROM_COMPANY = "select count(*) from company";
    private static final String SELECT_COUNT_FROM_COMPANY_WHERE_ID = "select count(*) from company where id = ?";
    private static final String SELECT_ID_NAME_FROM_COMPANY = "select id, name from company order by name";
    private static CompanyDaoImpl instance;

    /**
     * private ctor.
     */
    private CompanyDaoImpl() {
    }

    /**
     * @return unique dao instance.
     */
    public static CompanyDaoImpl getInstance() {
        if (instance == null) {
            instance = new CompanyDaoImpl();
        }
        return instance;
    }

    /**
     * @return Full company list from DB
     * @throws DaoException content couldn't be loaded
     */
    public List<Company> getCompanyList() throws DaoException {
        return DaoConnection.executeSelectQuery(SELECT_ID_NAME_FROM_COMPANY, new CompanyMapper());
    }

    /**
     * @param idCompany the id to check
     * @return true is company id is present in DB
     * @throws DaoException content couldn't be loaded
     */
    public boolean companyExists(Long idCompany) throws DaoException {
        return DaoConnection.executeQuery((Connection conn) -> {
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
     * @throws DaoException content couldn't be loaded
     */
    public Page<Company> getCompanyPage() throws DaoException {
        Long size = getCompanyCount();

        PageQuery<Company> command = (Page<Company> p) -> {
            Long startElem = PageUtils.getStartElem(p);
            String filter = String.format(" ORDER BY id LIMIT %d,%d", startElem, p.getPageSize());
            return DaoConnection.executeSelectQuery(SELECT_ID_NAME_FROM_COMPANY + filter, new CompanyMapper());
        };

        return new Page<Company>(command, size, 10L, 1L);
    }

    /**
     * @return the number of company in DB
     * @throws DaoException content couldn't be loaded
     */
    public Long getCompanyCount() throws DaoException {
        Long size = DaoConnection.executeSelectQuery(SELECT_COUNT_FROM_COMPANY, (ResultSet r) -> {
            return (r.next() ? r.getLong(1) : null);
        });
        return size;
    }

    /**
     * @param id id to delete
     * @throws DaoException failed to delete
     */
    public void deleteCompany(Long id) throws DaoException {

        DaoConnection.executeQuery((Connection conn) -> {

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
