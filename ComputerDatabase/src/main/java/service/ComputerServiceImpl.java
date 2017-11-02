package service;

import java.util.List;

import model.Computer;
import model.pages.Page;
import persistence.ComputerDaoImpl;
import persistence.exceptions.DaoException;
import persistence.querycommands.PageQuery;

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
     * @param ids ids list of the computer to delete
     * @throws DaoException content couldn't be loaded
     */
    public void deleteComputers(List<Long> ids) throws DaoException {
        computerDao.deleteComputers(ids);
    }

    /**
     * @param nbPage the page number to retrieve, starting at 1
     * @param pageSize number of elements by page
     * @return the first page of the full computer preview list from DB, with content not loaded yet (LAZY)
     * @throws DaoException content couldn't be loaded
     */
    public Page<Computer> getComputerPage(Long nbPage, Long pageSize) throws DaoException {
        PageQuery<Computer> pageQuery = (Long start, Long splitSize) -> {
            return computerDao.get(start, splitSize);
        };

        long startElem = (nbPage - 1) * pageSize;
        Long size = computerDao.getComputerTotalCount();
        return new Page<Computer>(pageQuery, startElem, size, pageSize);
    }


    /**
     * @param nbPage the page number to retrieve, starting at 1
     * @param pageSize number of elements by page
     * @param search search by ComputerName or CompanyName
     * @return the first page of the full computer preview list from DB
     * @throws DaoException content couldn't be loaded
     */
    public Page<Computer> getComputerPageWithSearch(Long nbPage, Long pageSize, String search) throws DaoException {
        PageQuery<Computer> pageQuery = (Long start, Long splitSize) -> {
            return computerDao.get(start, splitSize, search);
        };

        long startElem = (nbPage - 1) * pageSize;
        Long size = computerDao.getComputerTotalCount(search);
        Page<Computer> page = new Page<Computer>(pageQuery, startElem, size, pageSize);

        page.setSearch(search);

        return page;
    }

    /**
     * @param id id of the company to delete computers from
     * @throws DaoException deletion failed
     */
    public void deleteComputerByCompany(Long id) throws DaoException {
        computerDao.deleteComputerByCompany(id);
    }
}
