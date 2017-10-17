package dtos;

import java.time.LocalDate;

public class ComputerDto {
    private Long      id;
    private String    name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private String    companyName;
    private Boolean   selected;

    /**
     * @param name name
     * @param introduced introduced
     * @param discontinued discontinued
     * @param companyName companyName
     */
    public ComputerDto(String name, LocalDate introduced, LocalDate discontinued, String companyName) {
        this(null, name, introduced, discontinued, companyName);
    }

    /**
     * @param id unique id
     * @param name name
     * @param introduced introduced
     * @param discontinued discontinued
     * @param companyName companyName
     */
    public ComputerDto(Long id, String name, LocalDate introduced, LocalDate discontinued, String companyName) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.companyName = companyName;
        this.selected = false;
    }

    /**
     * @return String generated from id, name, dates, and companyId, to display in console later
     */
    @Override
    public String toString() {
        return "id=" + id + ", name=\"" + name + "\", introduced=" + introduced + ", discontinued=" + discontinued
                + ", companyName=" + companyName;
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

    public Long getId() {
        return id;
    }

    public String getSelectedStr() {
        return selected ? "1" : "0";
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName;
    }

}
