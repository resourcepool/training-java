package persistence;

import java.util.List;

import model.Computer;
import model.pages.Page;
import persistence.exceptions.DaoException;

public interface IComputerDao {

    /**
     * @return total number of computer in DB
     * @throws DaoException content couldn't be loaded
     */
    Long getComputerTotalCount() throws DaoException;

    /**
     * @param search filter to used
     * @return total number of computer in DB
     * @throws DaoException content couldn't be loaded
     */
    Long getComputerTotalCount(String search) throws DaoException;

    /**
     * @param id the computer id to search
     * @return the first computer corresponding exactly to @id
     * @throws DaoException content couldn't be loaded
     */
    Computer getComputerDetail(Long id) throws DaoException;

    /**
     * @param page number of total elements than can be loaded
     * @return the content of one computer page from DB
     * @throws DaoException content couldn't be loaded
     */
    List<Computer> get(Page<Computer> page) throws DaoException;

    /**
     * @param newComputer complete computer to create, without id
     * @return the id of the created computer
     * @throws DaoException content couldn't be loaded
     */
    Long createComputer(Computer newComputer) throws DaoException;

    /**
     * @param c full computer to update with id != null
     * @throws DaoException content couldn't be loaded
     */
    void updateComputer(Computer c) throws DaoException;

    /**
     * @param id id of the computer to delete
     * @throws DaoException content couldn't be loaded
     */
    void deleteComputer(Long id) throws DaoException;

    /**
     * @param id id of the computer to delete
     * @throws DaoException content couldn't be loaded
     */
    void deleteByCompany(Long id) throws DaoException;

    /**
     * @param ids ids to delete
     * @throws DaoException failed
     */
    void deleteComputers(List<Long> ids) throws DaoException;

}