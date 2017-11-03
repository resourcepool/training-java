package service;

import java.util.List;

import mapper.ComputerMapping;
import model.Computer;
import model.pages.Order;
import model.pages.Page;
import persistence.exceptions.DaoException;

public interface IComputerService {

    /**
     * @param id the computer id to search
     * @return the first computer corresponding exactly to @id
     * @throws DaoException content couldn't be loaded
     */
    Computer getComputerDetail(Long id) throws DaoException;

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
     * @param ids ids list of the computer to delete
     * @throws DaoException content couldn't be loaded
     */
    void deleteComputers(List<Long> ids) throws DaoException;

    /**
     * @param nbPage the page number to retrieve, starting at 1
     * @param pageSize number of elements by page
     * @return the first page of the full computer preview list from DB, with content not loaded yet (LAZY)
     * @throws DaoException content couldn't be loaded
     */
    Page<Computer> getPage(Long nbPage, Long pageSize) throws DaoException;

    /**
     * @param nbPage the page number to retrieve, starting at 1
     * @param pageSize number of elements by page
     * @param search search by ComputerName or CompanyName
     * @return the first page of the full computer preview list from DB
     * @throws DaoException content couldn't be loaded
     */
    Page<Computer> getPageWithSearch(Long nbPage, Long pageSize, String search) throws DaoException;

    /**
     * @param nbPage the page number to retrieve, starting at 1
     * @param pageSize number of elements by page
     * @param sort column to sort
     * @param order ASC or DESC
     * @return the first page of the full computer preview list from DB
     * @throws DaoException content couldn't be loaded
     */
    Page<Computer> getPageWithOrder(Long nbPage, Long pageSize, ComputerMapping sort, Order order) throws DaoException;

    /**
     * @param id id of the company to delete computers from
     * @throws DaoException deletion failed
     */
    void deleteComputerByCompany(Long id) throws DaoException;

}