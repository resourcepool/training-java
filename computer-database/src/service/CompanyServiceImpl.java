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

public class CompanyServiceImpl {

	public List<Company> getCompanyList() throws SQLException {
		return CompanyDao.getInstance().getCompanyList();
	}

	public boolean companyExists(Long idCompany) throws SQLException {
		return CompanyDao.getInstance().companyExists(idCompany);
	}

}
