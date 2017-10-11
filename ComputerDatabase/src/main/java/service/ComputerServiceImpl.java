package service;

import java.sql.SQLException;
import java.util.List;

import mapper.pages.Page;
import model.Computer;
import model.ComputerPreview;
import persistence.ComputerDao;

public class ComputerServiceImpl {

    private static ComputerDao computerDao = ComputerDao.getInstance();

    /**
     * @return the full list of computer, only name and id preview
     * @throws SQLException content couldn't be loaded
     */
    public List<ComputerPreview> getComputersList() throws SQLException {
        return computerDao.getComputersList();
    }

    /**
     * @param id the computer id to search
     * @return the first computer corresponding exactly to @id
     * @throws SQLException content couldn't be loaded
     */
    public Computer getComputerDetail(Long id) throws SQLException {
        return computerDao.getComputerDetail(id);
    }

    /**
     * @param name the name to search
     * @return the first computer corresponding exactly to @name
     * @throws SQLException content couldn't be loaded
     */
    public Computer getComputerDetail(String name) throws SQLException {
        return computerDao.getComputerDetail(name);
    }

    /**
     * @param newComputer complete computer to create, without id
     * @return the id of the created computer
     * @throws SQLException content couldn't be loaded
     */
    public Long createComputer(Computer newComputer) throws SQLException {
        return computerDao.createComputer(newComputer);
    }

    /**
     * @param c full computer to update with id != null
     * @throws SQLException content couldn't be loaded
     */
    public void updateComputer(Computer c) throws SQLException {
        computerDao.updateComputer(c);
    }

    /**
     * @param id id of the computer to delete
     * @throws SQLException content couldn't be loaded
     */
    public void deleteComputer(Long id) throws SQLException {
        computerDao.deleteComputer(id);
    }

    /**
     * @return the first page of the full computer preview list from DB
     * @throws SQLException content couldn't be loaded
     */
    public Page<ComputerPreview> getComputerPage() throws SQLException {

        return computerDao.getComputerPage();
    }

}
