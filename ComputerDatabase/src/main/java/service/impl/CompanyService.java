package service.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company;
import model.pages.Page;
import persistence.ICompanyDao;
import service.ICompanyService;
import service.IComputerService;

public class CompanyService implements ICompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);
    private ICompanyDao         companyDao;
    private IComputerService    computerService;

    /**
     * Private ctor.
     *
     * @param dao CompanyDao to access Data
     * @param computerService computerService to handle transactions
     */
    private CompanyService(ICompanyDao dao, IComputerService computerService) {
        this.companyDao = dao;
        this.computerService = computerService;
    }

    /**
     * @return Full company list from DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public List<Company> getList() {
        try {
            return companyDao.getCompanyList();

        } catch (SQLException e) {
            LOGGER.error("Companies list couldn't be loaded, reason \"" + e.getMessage() + "\"");
            throw new RuntimeException(e);
        }
    }

    /**
     * @param idCompany idCompany the id to check
     * @return true is company id is present in DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public boolean exists(Long idCompany) {
        if (idCompany == null) {
            throw new IllegalArgumentException();
        }
        try {
            return companyDao.companyExists(idCompany);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Transactional. see applicationContext.xml
     * @param id id to delete
     * @throws SQLException failed to delete
     */
    @Override
    public void delete(Long id) {

        try {
            computerService.deleteByCompany(id);
            companyDao.deleteCompany(id);

            LOGGER.info("Success delete company : " + id);
        } catch (SQLException e) {
            String msg = "failed to delete : " + e.getMessage();
            LOGGER.error(msg);
            throw new RuntimeException(e);
        }

    }

    /**
     * @return the first page of the full company list from DB
     */
    @Override
    public Page<Company> getPage() {
        try {
            return companyDao.getCompanyPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
