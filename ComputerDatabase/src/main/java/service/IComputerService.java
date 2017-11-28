package service;

import java.sql.SQLException;
import java.util.List;

import model.Computer;
import model.pages.Page;

public interface IComputerService {

    /**
     * @param id the computer id to search
     * @return the first computer corresponding exactly to @id
     */
    Computer getDetail(Long id);

    /**
     * @param newComputer complete computer to create, without id
     */
    void create(Computer newComputer);

    /**
     * @param c full computer to update with id != null
     */
    void update(Computer c);

    /**
     * @param id id of the computer to delete
     */
    void delete(Long id);

    /**
     * @param ids ids list of the computer to delete
     */
    void delete(List<Long> ids);

    /**
     * @param pageBuilder page request
     * @return the first page of the full computer preview list from DB
     */
    Page<Computer> loadPage(PageRequest<Computer> pageBuilder);

    /**
     * @param id id of the company to delete computers from
     */
    void deleteByCompany(Long id);

    /**
     * @param search filter to search (computer or company name like) or null
     * @return number of elem
     * @throws SQLException fail to load
     */
    Long getCount(String search);

}