package service;

import java.util.List;

import model.Computer;
import model.pages.Page;
import persistence.exceptions.DaoException;

public interface IComputerService {

    /**
     * @param id the computer id to search
     * @return the first computer corresponding exactly to @id
     * @throws DaoException content couldn't be loaded
     */
    Computer getDetail(Long id) throws DaoException;

    /**
     * @param newComputer complete computer to create, without id
     * @return the id of the created computer
     * @throws DaoException content couldn't be loaded
     */
    Long create(Computer newComputer) throws DaoException;

    /**
     * @param c full computer to update with id != null
     * @throws DaoException content couldn't be loaded
     */
    void update(Computer c) throws DaoException;

    /**
     * @param id id of the computer to delete
     * @throws DaoException content couldn't be loaded
     */
    void delete(Long id) throws DaoException;

    /**
     * @param ids ids list of the computer to delete
     * @throws DaoException content couldn't be loaded
     */
    void delete(List<Long> ids) throws DaoException;

    /**
     * @param pageBuilder page request
     * @return the first page of the full computer preview list from DB
     */
    Page<Computer> loadPage(PageRequest<Computer> pageBuilder);

    /**
     * @param id id of the company to delete computers from
     * @throws DaoException deletion failed
     */
    void deleteByCompany(Long id) throws DaoException;



}