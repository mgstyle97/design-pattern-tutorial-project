package io.wisoft.project.dao.dto.user.student;

import io.wisoft.project.dao.dto.user.UserDTO;

public class StudentDTO extends UserDTO {

    private int credit;

    public StudentDTO(String id, String password, String name, String dept) {
        super(id, password, name, dept);
    }

    public int getCredit() { return this.credit; }

    public void setCredit(int credit) { this.credit = credit; }

}
