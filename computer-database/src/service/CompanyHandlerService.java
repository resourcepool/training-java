package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.CompanyModel;
import model.ComputerModel;
import model.ComputerModelPreview;
import persistence.ComputerDao;
import ui.UiConsole;

public class CompanyHandlerService implements ICompanyHandlerService {

	private ComputerDao dao;
	private UiConsole console;

	public CompanyHandlerService(ComputerDao dao, UiConsole console) {
		this.dao = dao;
		this.console = console;
	}

	@Override
	public List<ComputerModelPreview> getComputersList() {
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
	public List<CompanyModel> getCompaniesList() {
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
	public ComputerModel getComputerDetail(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComputerModel getComputerDetail(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Long createComputer(ComputerModel newComputer) {
		return dao.createComputer(newComputer);
	}

	@Override
	public void updateComputer(ComputerModel c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteComputer(ComputerModel c) {
		// TODO Auto-generated method stub
	}


}
