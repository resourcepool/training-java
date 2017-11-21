package model;

import javax.servlet.http.HttpServletRequest;

import validators.ValidationUtils;

public class ComputerDto {

    private String id;
    private String name;
    private String introduced;
    private String discontinued;
    private String companyId;


    /**
     * @param req init params from httpRequest params
     */
    public ComputerDto(HttpServletRequest req) {
        this.name = req.getParameter(ValidationUtils.COMPUTER_NAME);
        this.introduced = req.getParameter(ValidationUtils.INTRODUCED);
        this.discontinued = req.getParameter(ValidationUtils.DISCONTINUED);
        this.companyId = req.getParameter(ValidationUtils.COMPANY_ID);
        this.id = req.getParameter(ValidationUtils.ID);
    }

    public String getName() {
        return name;
    }

    public String getIntroduced() {
        return introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public String getCompanyId() {
        return companyId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
