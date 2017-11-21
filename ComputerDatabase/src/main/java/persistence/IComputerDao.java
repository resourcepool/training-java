package persistence;

import java.sql.SQLException;
import java.util.List;

import model.Computer;
import model.pages.Page;

public interface IComputerDao {

    /**
     * @return total number of computer in DB
     * @throws SQLException content couldn't be loaded
     */
    Long getComputerTotalCount() throws SQLException;

    /**
     * @param search filter to used
     * @return total number of computer in DB
     * @throws SQLException content couldn't be loaded
     */
    Long getComputerTotalCount(String search) throws SQLException;

    /**
     * @param id the computer id to search
     * @return the first computer corresponding exactly to @id
     * @throws SQLException content couldn't be loaded
     */
    Computer getComputerDetail(Long id) throws SQLException;

    /**
     * @param page number of total elements than can be loaded
     * @return the content of one computer page from DB
     * @throws SQLException content couldn't be loaded
     */
    List<Computer> get(Page<Computer> page) throws SQLException;

    /**
     * @param newComputer complete computer to create, without id
     * @return the id of the created computer
     * @throws SQLException content couldn't be loaded
     */
    Long createComputer(Computer newComputer) throws SQLException;

    /**
     * @param c full computer to update with id != null
     * @throws SQLException content couldn't be loaded
     */
    void updateComputer(Computer c) throws SQLException;

    /**
     * @param id id of the computer to delete
     * @throws SQLException content couldn't be loaded
     */
    void deleteComputer(Long id) throws SQLException;

    /**
     * @param id id of the computer to delete
     * @throws SQLException content couldn't be loaded
     */
    void deleteByCompany(Long id) throws SQLException;

    /**
     * @param ids ids to delete
     * @throws SQLException failed
     */
    void deleteComputers(List<Long> ids) throws SQLException;

}