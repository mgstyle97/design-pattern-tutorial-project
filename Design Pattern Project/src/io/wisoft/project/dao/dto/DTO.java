package io.wisoft.project.dao.dto;


public class DTO {

    private String id = null;

    public DTO(String id) {
        this.id = id;
    }

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String toString() {
        return "id: " + this.id;
    }

}
