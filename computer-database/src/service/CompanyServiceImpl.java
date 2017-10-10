package service;

import java.sql.SQLException;
import java.util.List;

import mapper.pages.Page;
import model.Company;
import persistence.CompanyDao;

public class CompanyServiceImpl {

	private static final CompanyDao companyDao = CompanyDao.getInstance();

	public List<Company> getCompanyList() throws SQLException {
		return companyDao.getCompanyList();
	}

	public boolean companyExists(Long idCompany) throws SQLException {
		return companyDao.companyExists(idCompany);
	}

	public Page<Company> getCompanyPage() throws SQLException {
		
		return companyDao.getCompanyPage();
	}

}
