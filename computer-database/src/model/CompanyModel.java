package model;

public class CompanyModel {
	private Long id;
	private String name;
	
	public CompanyModel(String name) {
		this.name = name;
	}

	public CompanyModel(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CompanyModel [id=" + id + ", name=" + name + "]";
	}
}
