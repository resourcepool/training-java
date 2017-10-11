package service;

import java.sql.SQLException;
import java.util.List;

import mapper.pages.Page;
import model.Company;
import persistence.CompanyDao;

public class CompanyServiceImpl {

    private static CompanyDao companyDao = CompanyDao.getInstance();

    /**
     * @return Full company list from DB
     * @throws SQLException content couldn't be loaded
     */
    public List<Company> getCompanyList() throws SQLException {
        return companyDao.getCompanyList();
    }

    /**
     * @param idCompany idCompany the id to check
     * @return true is company id is present in DB
     * @throws SQLException content couldn't be loaded
     */
    public boolean companyExists(Long idCompany) throws SQLException {
        return companyDao.companyExists(idCompany);
    }

    /**
     * @return the first page of the full company list from DB
     * @throws SQLException content couldn't be loaded
     */
    public Page<Company> getCompanyPage() throws SQLException {

        return companyDao.getCompanyPage();
    }

}
