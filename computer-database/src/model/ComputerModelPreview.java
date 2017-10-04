package model;

public class ComputerModelPreview {
	private long id;
	private String name;
	
	public ComputerModelPreview(String name) {
		this.id = RandomStatic.nextLong();
		this.name = name;
	}
	
	public ComputerModelPreview(long id, String name) {
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
		return "ComputerModelPreview [id=" + id + ", name=" + name + "]";
	}

}
