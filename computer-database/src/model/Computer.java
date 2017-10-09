package model;
import java.time.LocalDate;

public class Computer {
	
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Long companyId;
	
	public Computer(String name, LocalDate introduced, LocalDate discontinued, Long company_id)
	{
		this(null, name, introduced, discontinued, company_id);
	}
	
	public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued, Long company_id)
	{
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = company_id;
	}
	
	@Override
	public String toString() {
		return "id=" + id + ", name=\"" + name + "\", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company_id=" + companyId ;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long company_id) {
		this.companyId = company_id;
	}

	public Long getId() {
		return id;
	}
}

