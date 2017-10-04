package service;

import java.util.List;

import model.CompanyModel;
import model.ComputerModel;
import model.ComputerModelPreview;

public interface ICompanyHandlerService {

	List<ComputerModelPreview> getComputersList();

	List<CompanyModel> getCompaniesList();

	ComputerModel getComputerDetail(Long id);
	
	ComputerModel getComputerDetail(String name);
	
	Long createComputer(ComputerModel newComputer);

	void updateComputer(ComputerModel c);

	void deleteComputer(ComputerModel c);
}