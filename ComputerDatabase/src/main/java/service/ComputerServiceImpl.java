package service;

import java.util.List;

import mapper.pages.Page;
import model.Computer;
import model.ComputerPreview;
import persistence.ComputerDao;
import persistence.exceptions.DaoException;

public class ComputerServiceImpl {

    private static ComputerServiceImpl instance;
    private ComputerDao                computerDao;

    /**
     * Private ctor.
     * @param dao CompanyDao to access Data
     */
    private ComputerServiceImpl(ComputerDao dao) {
        computerDao = dao;
    }

    /**
     * @return a loaded Service, ready to work
     */
    public static ComputerServiceImpl getInstance() {
        if (instance == null) {
            instance = new ComputerServiceImpl(ComputerDao.getInstance());
        }
        return instance;
    }

    /**
     * @return the full list of computer, only name and id preview
     * @throws DaoException content couldn't be loaded
     */
    public List<ComputerPreview> getComputersList() throws DaoException {
        return computerDao.getComputersList();
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
     * @return the first page of the full computer preview list from DB
     * @throws DaoException content couldn't be loaded
     */
    public Page<ComputerPreview> getComputerPage() throws DaoException {

        return computerDao.getComputerPage();
    }

}
