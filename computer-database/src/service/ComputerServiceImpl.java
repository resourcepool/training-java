package service;

import java.sql.SQLException;
import java.util.List;

import model.Computer;
import model.ComputerPreview;
import persistence.ComputerDao;

public class ComputerServiceImpl {

	public List<ComputerPreview> getComputersList() throws SQLException {
		return ComputerDao.getInstance().getComputersList();
	}

	public Computer getComputerDetail(Long id) throws SQLException {
		return ComputerDao.getInstance().getComputerDetail(id);
	}

	public Computer getComputerDetail(String name) throws SQLException {
		return ComputerDao.getInstance().getComputerDetail(name);
	}
	
	public Long createComputer(Computer newComputer) throws SQLException {
		return ComputerDao.getInstance().createComputer(newComputer);
	}

	public void updateComputer(Computer c) {
		// TODO Auto-generated method stub

	}

	public void deleteComputer(Computer c) {
		// TODO Auto-generated method stub
	}

}
