package service;

import java.util.List;

import model.Company;
import model.Computer;
import model.ComputerPreview;

public interface ICompanyHandlerService {

	List<ComputerPreview> getComputersList();

	List<Company> getCompaniesList();

	Computer getComputerDetail(Long id);
	
	Computer getComputerDetail(String name);
	
	void createComputer(Computer newComputer);

	void updateComputer(Computer c);

	void deleteComputer(Computer c);
}