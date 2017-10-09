package service;

import java.sql.SQLException;
import java.util.List;

import model.Company;
import persistence.CompanyDao;

public class CompanyServiceImpl {

	public List<Company> getCompanyList() throws SQLException {
		return CompanyDao.getInstance().getCompanyList();
	}

	public boolean companyExists(Long idCompany) throws SQLException {
		return CompanyDao.getInstance().companyExists(idCompany);
	}

}
