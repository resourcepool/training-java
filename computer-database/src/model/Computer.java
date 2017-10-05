package model;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Computer {
	
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Long company_id;
	
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
		this.company_id = company_id;
	}
	
	@Override
	public String toString() {
		return "id=" + id + ", name=\"" + name + "\", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company_id=" + company_id ;
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
		return company_id;
	}

	public void setCompanyId(Long company_id) {
		this.company_id = company_id;
	}
}

