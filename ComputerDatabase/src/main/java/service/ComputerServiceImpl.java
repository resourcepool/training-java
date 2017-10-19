package service;

import java.util.List;

import model.Computer;
import model.pages.Page;
import persistence.ComputerDaoImpl;
import persistence.exceptions.DaoException;

public class ComputerServiceImpl {

    private static ComputerServiceImpl instance;
    private ComputerDaoImpl            computerDao;

    /**
     * Private ctor.
     * @param dao CompanyDao to access Data
     */
    private ComputerServiceImpl(ComputerDaoImpl dao) {
        computerDao = dao;
    }

    /**
     * @return a loaded Service, ready to work
     */
    public static ComputerServiceImpl getInstance() {
        if (instance == null) {
            instance = new ComputerServiceImpl(ComputerDaoImpl.getInstance());
        }
        return instance;
    }

    /**
     * @param id the computer id to search
     * @return the first computer corresponding exactly to @id
     * @throws DaoException content couldn't be loaded
     */
    public Computer getComputerDetail(Long id) throws DaoException {
        return computerDao.getComputerDetail(id);
    }

    /**
     * @param name the name to search
     * @return the first computer corresponding exactly to @name
     * @throws DaoException content couldn't be loaded
     */
    public Computer getComputerDetail(String name) throws DaoException {
        return computerDao.getComputerDetail(name);
    }

    /**
     * @param newComputer complete computer to create, without id
     * @return the id of the created computer
     * @throws DaoException content couldn't be loaded
     */
    public Long createComputer(Computer newComputer) throws DaoException {
        return computerDao.createComputer(newComputer);
    }

    /**
     * @param c full computer to update with id != null
     * @throws DaoException content couldn't be loaded
     */
    public void updateComputer(Computer c) throws DaoException {
        computerDao.updateComputer(c);
    }

    /**
     * @param id id of the computer to delete
     * @throws DaoException content couldn't be loaded
     */
    public void deleteComputer(Long id) throws DaoException {
        computerDao.deleteComputer(id);
    }

    /**
     * @param start index entities to start at (start at 0)
     * @param size number of elements
     * @return the first page of the full computer preview list from DB
     * @throws DaoException content couldn't be loaded
     */
    public Page<Computer> getComputerEntities(Long start, Long size) throws DaoException {
        return computerDao.getComputerPage(start, size);
    }

    /**
     * @param nbPage the page number to retrieve, starting at 1
     * @param pageSize number of elements by page
     * @return the first page of the full computer preview list from DB
     * @throws DaoException content couldn't be loaded
     */
    public Page<Computer> getComputerPage(Long nbPage, Long pageSize) throws DaoException {
        return getComputerEntities((nbPage - 1) * pageSize, pageSize);
    }


    /**
     * @return the full list of computer, only name and id preview
     * @throws DaoException content couldn't be loaded
     */
    public List<Computer> getComputersList() throws DaoException {
        return computerDao.getComputersList();
    }

    /**
     * @param splitSize number of element by page
     * @return total of elements / splitSize
     * @throws DaoException content couldn't be loaded
     */
    public long getPageTotal(Long splitSize) throws DaoException {
        return computerDao.getComputerTotalCount() / splitSize;
    }

}
