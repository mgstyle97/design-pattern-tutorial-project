package io.wisoft.project.dao.dto.user;

import io.wisoft.project.dao.dto.DTO;
import io.wisoft.project.common.State;

public class UserDTO extends DTO {

    private String password, name, dept;
    private State state;

    public UserDTO(State state) {
        super(null);
        this.state = state;
    }

    public UserDTO(String id, String password, String name, String dept) {
        super(id);
        this.password = password;
        this.name = name;
        this.dept = dept;
    }

    public String getPassword() { return this.password; }

    public String getName() { return this.name; }

    public String getDept() { return this.dept; }

    public State getState() { return this.state; }

    public void setPassword(String password) { this.password = password; }

    public void setName(String name) { this.name = name; }

    public void setDept(String dept) { this.dept = dept; }

    public void setState(State state) { this.state = state; }

    @Override
    public String toString() {

        String str = super.toString();
        str += "\n이름: " + this.name;
        str += "\n전공: " + this.dept;

        return str;
    }

}
