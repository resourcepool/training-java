package model;

public class ComputerPreview {
    private String id;
    private String name;

    /**
     * @param name name
     */
    public ComputerPreview(String name) {
        this.name = name;
    }

    /**
     * @param id unique id
     * @param name name
     */
    public ComputerPreview(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String generated from id, name, to display in console later
     */
    @Override
    public String toString() {
        return "ComputerModelPreview [id=" + id + ", name=" + name + "]";
    }

}
