package service;

import java.util.ArrayList;

import model.CompanyModel;
import model.ComputerModel;
import model.ComputerModelPreview;

public interface ICompanyHandlerService {

	ArrayList<ComputerModelPreview> getComputersList();

	ArrayList<CompanyModel> getCompaniesList();

	ComputerModel getComputerDetail(long id);
	
	ComputerModel getComputerDetail(String name);
	
	void CreateComputer(ComputerModel newComputer);

	void UpdateComputer(ComputerModel c);

	void DeleteComputer(ComputerModel c);
}