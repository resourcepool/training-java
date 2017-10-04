package model;
import java.util.Date;

public class ComputerModel {
	
	private long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private long company_id;
	
	public ComputerModel(String name, Date introduced, Date discontinued, long company_id)
	{
		this(-1, name, introduced, discontinued, company_id);
	}
	
	public ComputerModel(long id, String name, Date introduced, Date discontinued, long company_id)
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

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public long getCompanyId() {
		return company_id;
	}

	public void setCompanyId(long company_id) {
		this.company_id = company_id;
	}
}

