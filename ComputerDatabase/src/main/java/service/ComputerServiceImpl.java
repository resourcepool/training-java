package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Computer;
import model.pages.Page;
import persistence.ComputerDaoImpl;
import persistence.exceptions.DaoException;
import persistence.querycommands.PageQuery;

public class ComputerServiceImpl implements IComputerService {

    private static final Logger     LOGGER = LoggerFactory.getLogger(ComputerServiceImpl.class);
    private static IComputerService instance;
    private ComputerDaoImpl         computerDao;

    /**
     * Private ctor.
     *
     * @param dao CompanyDao to access Data
     */
    private ComputerServiceImpl(ComputerDaoImpl dao) {
        computerDao = dao;
    }

    /**
     *
     *
     * /**
     *
     * @return a loaded Service, ready to work
     */
    public static IComputerService getInstance() {
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
    @Override
    public Computer getComputerDetail(Long id) throws DaoException {
        try {
            return computerDao.getComputerDetail(id);
        } catch (DaoException e) {
            LOGGER.error("Computer " + id + " could not be loaded");
            throw e;
        }
    }

    /**
     * @param newComputer complete computer to create, without id
     * @return the id of the created computer
     * @throws DaoException content couldn't be loaded
     */
    @Override
    public Long createComputer(Computer newComputer) throws DaoException {
        try {
            return computerDao.createComputer(newComputer);
        } catch (DaoException e) {
            String msg = "Computer cannot be created, reason \"" + e.getMessage() + "\"";
            LOGGER.error(msg);
            throw e;
        }
    }

    /**
     * @param c full computer to update with id != null
     * @throws DaoException content couldn't be loaded
     */
    @Override
    public void updateComputer(Computer c) throws DaoException {
        try {
            computerDao.updateComputer(c);
        } catch (DaoException e) {
            String msg = "Computer cannot be edited, reason \"" + e.getMessage() + "\"";
            LOGGER.error(msg);
        }
    }

    /**
     * @param id id of the computer to delete
     * @throws DaoException content couldn't be loaded
     */
    @Override
    public void deleteComputer(Long id) throws DaoException {
        computerDao.deleteComputer(id);
    }

    /**
     * @param ids ids list of the computer to delete
     * @throws DaoException content couldn't be loaded
     */
    @Override
    public void deleteComputers(List<Long> ids) throws DaoException {
        try {
            computerDao.deleteComputers(ids);

        } catch (DaoException e) {
            String msg = "failed to execute deletion, reason :" + e.getMessage();
            LOGGER.error(msg);
        }

    }

    /**
     * @param pageBuilder page request
     * @return the first page of the full computer preview list from DB
     * @throws DaoException content couldn't be loaded
     */
    @Override
    public Page<Computer> loadPage(PageBuilder<Computer> pageBuilder) {
        PageQuery<Computer> pageQuery = getPageQuery();

        try {
            Long size = getCount(pageBuilder.getSearch());
            Page<Computer> page = pageBuilder.build(pageQuery, size);
            return page.load();
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     * @param search filter to search (computer or company name like) or null
     * @return number of elem
     * @throws DaoException fail to load
     */
    private Long getCount(String search) throws DaoException {
        return search == null ? computerDao.getComputerTotalCount() : computerDao.getComputerTotalCount(search);
    }

    private PageQuery<Computer> getPageQuery() {
        return (Page<Computer> page) -> {
            return computerDao.get(page);
        };
    }

    /**
     * @param id id of the company to delete computers from
     * @throws DaoException deletion failed
     */
    @Override
    public void deleteComputerByCompany(Long id) throws DaoException {
        computerDao.deleteComputerByCompany(id);
    }

}
