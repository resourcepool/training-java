package service;

import java.util.List;

import model.Company;
import model.pages.Page;
import persistence.exceptions.DaoException;

public interface ICompanyService {

    /**
     * @return Full company list from DB
     * @throws DaoException content couldn't be loaded
     */
    List<Company> getList() throws DaoException;

    /**
     * @param idCompany idCompany the id to check
     * @return true is company id is present in DB
     * @throws DaoException content couldn't be loaded
     */
    boolean exists(Long idCompany) throws DaoException;

    /**
     * @return the first page of the full company list from DB
     * @throws DaoException content couldn't be loaded
     */
    Page<Company> getPage() throws DaoException;

    /**
     * @param id id to delete
     * @throws DaoException failed to delete
     */
    void delete(Long id) throws DaoException;

}