package service;

public class Services {
    private CompanyServiceImpl  companyService;
    private ComputerServiceImpl computerService;

    /**
     * ctor, keep one instance of each service.
     */
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
