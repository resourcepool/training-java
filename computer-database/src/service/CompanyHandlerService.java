package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Company;
import model.Computer;
import model.ComputerPreview;
import persistence.CompanyDao;
import persistence.ComputerDao;
import ui.UiConsole;

public class CompanyHandlerService implements ICompanyHandlerService {

	private UiConsole console;

	public CompanyHandlerService(UiConsole console) {
		this.console = console;
	}

	@Override
	public List<ComputerPreview> getComputersList() {
		try 
		{
			return ComputerDao.getInstance().getComputersList();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			console.write(e);
			return null;
		}
	}

	@Override
	public List<Company> getCompaniesList() {
		try
		{
			return CompanyDao.getInstance().getCompanyList();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			console.write(e);
		}
		return null;
	}

	@Override
	public Computer getComputerDetail(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Computer getComputerDetail(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void createComputer(Computer newComputer) {
		try {
			ComputerDao.getInstance().createComputer(newComputer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateComputer(Computer c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteComputer(Computer c) {
		// TODO Auto-generated method stub
	}


}
