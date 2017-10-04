package service;

import java.sql.SQLException;
import java.util.ArrayList;

import model.CompanyModel;
import model.ComputerModel;
import model.ComputerModelPreview;
import persistence.ComputersDao;
import ui.UiConsole;

public class CompanyHandlerService implements ICompanyHandlerService {

	private ComputersDao dao;
	private UiConsole console;

	public CompanyHandlerService(ComputersDao dao, UiConsole console) {
		this.dao = dao;
		this.console = console;
	}

	@Override
	public ArrayList<ComputerModelPreview> getComputersList() {
		try 
		{
			return dao.getComputersList();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			console.write(e);
			return null;
		}
	}

	@Override
	public ArrayList<CompanyModel> getCompaniesList() {
		try
		{
			return dao.getCompaniesList();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			console.write(e);
		}
		return null;
	}

	@Override
	public ComputerModel getComputerDetail(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComputerModel getComputerDetail(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long CreateComputer(ComputerModel newComputer) {
		try
		{
			dao.createComputer(newComputer);
		}

	}

	@Override
	public void UpdateComputer(ComputerModel c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void DeleteComputer(ComputerModel c) {
		// TODO Auto-generated method stub

	}


}
