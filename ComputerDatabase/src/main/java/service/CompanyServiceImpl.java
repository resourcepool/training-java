package service;

import java.util.List;

import mapper.pages.Page;
import model.Company;
import persistence.CompanyDao;
import persistence.exceptions.DaoException;

public class CompanyServiceImpl {

    private static CompanyServiceImpl instance;
    private CompanyDao companyDao;

    /**
     * Private ctor.
     * @param dao CompanyDao to access Data
     */
    private CompanyServiceImpl(CompanyDao dao) {
        companyDao = dao;
    }

    /**
     * @return a loaded Service, ready to work
     */
    public static CompanyServiceImpl getInstance() {
        if (instance == null) {
            instance = new CompanyServiceImpl(CompanyDao.getInstance());
        }
        return instance;
    }

    /**
     * @return Full company list from DB
     * @throws DaoException content couldn't be loaded
     */
    public List<Company> getCompanyList() throws DaoException {
        return companyDao.getCompanyList();
    }

    /**
     * @param idCompany idCompany the id to check
     * @return true is company id is present in DB
     * @throws DaoException content couldn't be loaded
     */
    public boolean companyExists(Long idCompany) throws DaoException {
        if (idCompany == null) {
            throw new NullPointerException();
        }
        return companyDao.companyExists(idCompany);
    }

    /**
     * @return the first page of the full company list from DB
     * @throws DaoException content couldn't be loaded
     */
    public Page<Company> getCompanyPage() throws DaoException {

        return companyDao.getCompanyPage();
    }

}
