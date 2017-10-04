package model;

public class ComputerModelPreview {
	private Long id;
	private String name;
	
	public ComputerModelPreview(String name) {
		this.name = name;
	}
	
	public ComputerModelPreview(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(Long id) {
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
		return "ComputerModelPreview [id=" + id + ", name=" + name + "]";
	}

}
