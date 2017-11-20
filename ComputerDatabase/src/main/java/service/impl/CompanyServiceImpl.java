package service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company;
import model.pages.Page;
import persistence.ICompanyDao;
import persistence.exceptions.DaoException;
import service.ICompanyService;
import service.IComputerService;

public class CompanyServiceImpl implements ICompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);
    private ICompanyDao         companyDao;
    private IComputerService    computerService;

    /**
     * Private ctor.
     *
     * @param dao CompanyDao to access Data
     * @param computerService computerService to handle transactions
     */
    private CompanyServiceImpl(ICompanyDao dao, IComputerService computerService) {
        this.companyDao = dao;
        this.computerService = computerService;
    }

    /**
     * @return Full company list from DB
     * @throws DaoException content couldn't be loaded
     */
    @Override
    public List<Company> getList() throws DaoException {
        try {
            return companyDao.getCompanyList();

        } catch (DaoException e) {
            LOGGER.error("Companies list couldn't be loaded, reason \"" + e.getMessage() + "\"");
            throw e;
        }
    }

    /**
     * @param idCompany idCompany the id to check
     * @return true is company id is present in DB
     * @throws DaoException content couldn't be loaded
     */
    @Override
    public boolean exists(Long idCompany) throws DaoException {
        if (idCompany == null) {
            throw new IllegalArgumentException();
        }
        return companyDao.companyExists(idCompany);
    }

    /**
     * @return the first page of the full company list from DB
     * @throws DaoException content couldn't be loaded
     */
    @Override
    public Page<Company> getPage() throws DaoException {

        return companyDao.getCompanyPage();
    }

    /**
     * Transactional. see applicationContext.xml
     * @param id id to delete
     * @throws DaoException failed to delete
     */
    @Override
    public void delete(Long id) throws DaoException {

        try {
            computerService.deleteByCompany(id);
            companyDao.deleteCompany(id);
        } catch (DaoException e) {
            String msg = "failed to delete : " + e.getMessage();
            LOGGER.error(msg);
            throw e;
        }

    }
}
