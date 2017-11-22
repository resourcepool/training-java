package service.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.Computer;
import model.pages.Page;
import persistence.IComputerDao;
import persistence.querycommands.PageQuery;
import service.IComputerService;
import service.PageRequest;

@Service("computerService")
@Transactional(readOnly = true, rollbackFor = SQLException.class)
public class ComputerService implements IComputerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
    private IComputerDao        computerDao;

    /**
     * Private ctor.
     *
     * @param dao CompanyDao to access Data
     */
    @Autowired
    private ComputerService(IComputerDao dao) {
        computerDao = dao;
    }

    /**
     * @param id the computer id to search
     * @return the first computer corresponding exactly to @id
     * @throws SQLException content couldn't be loaded
     */
    @Override
    public Computer getDetail(Long id) {
        try {
            return computerDao.getComputerDetail(id);
        } catch (SQLException e) {
            LOGGER.error("Computer " + id + " could not be loaded");
            throw new RuntimeException(e);
        }
    }

    /**
     * @param newComputer complete computer to create, without id
     * @throws SQLException content couldn't be loaded
     */
    @Override
    @Transactional(readOnly = false)
    public void create(Computer newComputer) {
        try {
            Long id = computerDao.createComputer(newComputer);
            newComputer.setId(id);
        } catch (SQLException e) {
            String msg = "Computer cannot be created, reason \"" + e.getMessage() + "\"";
            LOGGER.error(msg);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param c full computer to update with id != null
     * @throws SQLException content couldn't be loaded
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Computer c) {
        try {
            computerDao.updateComputer(c);
        } catch (SQLException e) {
            String msg = "Computer cannot be edited, reason \"" + e.getMessage() + "\"";
            LOGGER.error(msg);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id id of the computer to delete
     * @throws SQLException content couldn't be loaded
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        try {
            computerDao.deleteComputer(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param ids ids list of the computer to delete
     * @throws SQLException content couldn't be loaded
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(List<Long> ids) {
        try {
            computerDao.deleteComputers(ids);

        } catch (SQLException e) {
            String msg = "failed to execute deletion, reason :" + e.getMessage();
            LOGGER.error(msg);
            throw new RuntimeException(e);
        }

    }

    /**
     * @param request page request
     * @return the first page of the full computer preview list from DB
     * @throws SQLException content couldn't be loaded
     */
    @Override
    @Transactional(readOnly = false)
    public Page<Computer> loadPage(PageRequest<Computer> request) {
        try {
            PageQuery<Computer> pageQuery = (Page<Computer> page) -> computerDao.get(page);
            Long size = getCount(request.getSearch());
            return request.build(pageQuery, size).load();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * @param search filter to search (computer or company name like) or null
     * @return number of elem
     * @throws SQLException fail to load
     */
    private Long getCount(String search) {
        try {
            return search == null ? computerDao.getComputerTotalCount() : computerDao.getComputerTotalCount(search);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id id of the company to delete computers from
     * @throws SQLException deletion failed
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteByCompany(Long id) {
        try {
            computerDao.deleteByCompany(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
