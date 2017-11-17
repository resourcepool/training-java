package persistence;

import java.util.List;

import model.Company;
import model.pages.Page;
import persistence.exceptions.DaoException;

public interface ICompanyDao {

    /**
     * @return Full company list from DB
     * @throws DaoException content couldn't be loaded
     */
    List<Company> getCompanyList() throws DaoException;

    /**
     * @param idCompany the id to check
     * @return true is company id is present in DB
     * @throws DaoException content couldn't be loaded
     */
    boolean companyExists(Long idCompany) throws DaoException;

    /**
     * @return the first page of the full company list from DB
     * @throws DaoException content couldn't be loaded
     */
    Page<Company> getCompanyPage() throws DaoException;

    /**
     * @return the number of company in DB
     * @throws DaoException content couldn't be loaded
     */
    Long getCompanyCount() throws DaoException;

    /**
     * @param id id to delete
     * @throws DaoException failed to delete
     */
    void deleteCompany(Long id) throws DaoException;

}