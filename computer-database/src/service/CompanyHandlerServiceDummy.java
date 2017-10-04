package service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.CompanyModel;
import model.ComputerModel;
import model.ComputerModelPreview;
import persistence.ComputersDao;

public class CompanyHandlerServiceDummy implements ICompanyHandlerService {
	
	
	public CompanyHandlerServiceDummy() {
		
	}
	
	private static Date getDate(int year, int month, int day)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar.getTime();
		
	}
	
	@Override
	public ArrayList<ComputerModelPreview> getComputersList()
	{
		ArrayList<ComputerModelPreview> arrayList = new ArrayList<>();
		
		arrayList.add(new ComputerModelPreview("name2"));
		arrayList.add(new ComputerModelPreview("name3"));
		arrayList.add(new ComputerModelPreview("nameAnother"));
	
		return arrayList;
	}
	
	@Override
	public ArrayList<CompanyModel> getCompaniesList()
	{
		ArrayList<CompanyModel> arrayList = new ArrayList<CompanyModel>();
		arrayList.add(new CompanyModel(55, "Ebiz"));
		return arrayList;
	}
	
	@Override
	public ComputerModel getComputerDetail(long id)
	{
		return new ComputerModel("name", getDate(2017, 07, 07), null, 52);
	}
	

	@Override
	public ComputerModel getComputerDetail(String name) {
		//TODO
		return null;
	}
	
	@Override
	public void CreateComputer(ComputerModel newComputer)
	{
		//TODO
	}
	
	@Override
	public void UpdateComputer(ComputerModel c)
	{
		//TODO
	}
	
	@Override
	public void DeleteComputer(ComputerModel c)
	{
		//TODO
	}

}
