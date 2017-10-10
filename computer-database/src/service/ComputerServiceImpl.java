package service;

import java.sql.SQLException;
import java.util.List;

import mapper.pages.Page;
import model.Computer;
import model.ComputerPreview;
import persistence.ComputerDao;

public class ComputerServiceImpl {

	private static final ComputerDao computerDao = ComputerDao.getInstance();

	public List<ComputerPreview> getComputersList() throws SQLException {
		return computerDao.getComputersList();
	}

	public Computer getComputerDetail(Long id) throws SQLException {
		return computerDao.getComputerDetail(id);
	}

	public Computer getComputerDetail(String name) throws SQLException {
		return computerDao.getComputerDetail(name);
	}
	
	public Long createComputer(Computer newComputer) throws SQLException {
		return computerDao.createComputer(newComputer);
	}

	public void updateComputer(Computer c) throws SQLException {
		computerDao.updateComputer(c);
	}

	public void deleteComputer(Long id) throws SQLException {
		computerDao.deleteComputer(id);
	}

	public Page<ComputerPreview> getComputerPage() throws SQLException {
		
		return computerDao.getComputerPage();
	}

}
