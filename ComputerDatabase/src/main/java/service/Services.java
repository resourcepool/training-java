package service;

public class Services {
	private CompanyServiceImpl companyService;
	private ComputerServiceImpl computerService;
	
	public Services() {
		companyService = new CompanyServiceImpl();
		computerService = new ComputerServiceImpl();
	}
	
	public CompanyServiceImpl getCompanyService() {
		return companyService;
	}
	public ComputerServiceImpl getComputerService() {
		return computerService;
	}
}
