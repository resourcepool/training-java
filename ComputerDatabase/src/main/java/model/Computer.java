package model;

import java.time.LocalDate;

public class Computer {

    private Long      id;
    private String    name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company   company;

    /**
     * @param name name
     * @param introduced introduced
     * @param discontinued discontinued
     * @param companyId companyId
     */
    public Computer(String name, LocalDate introduced, LocalDate discontinued, Long companyId) {
        this(null, name, introduced, discontinued, new Company(companyId, null));
    }

    /**
     * @param name name
     * @param introduced introduced
     * @param discontinued discontinued
     * @param company company
     */
    public Computer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
        this(null, name, introduced, discontinued, company);
    }

    /**
     * @param id id
     * @param name name
     * @param introduced introduced
     * @param discontinued discontinued
     * @param companyId companyId
     */
    public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued, Long companyId) {
        this(id, name, introduced, discontinued, new Company(companyId, null));
    }

    /**
     * @param id unique id
     * @param name name
     * @param introduced introduced
     * @param discontinued discontinued
     * @param company company
     */
    public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }

    /**
     * @return String generated from id, name, dates, and companyId, to display in console later
     */
    @Override
    public String toString() {
        return "id=" + id + ", name=\"" + name + "\", introduced=" + introduced + ", discontinued=" + discontinued
                + ", company={" + company.toString() + "}";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Long getId() {
        return id;
    }
}
