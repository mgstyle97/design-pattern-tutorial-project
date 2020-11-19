package io.wisoft.project.dao.dto.user.professor;

import io.wisoft.project.dao.dto.user.UserDTO;

public class ProfessorDTO extends UserDTO {

    private int numOfLecture;

    public ProfessorDTO(String id, String password, String name, String dept) {
        super(id, password, name, dept);
    }

    public int getNumOfLecture() { return this.numOfLecture; }

    public void setNumOfLecture(int numOfLecture) { this.numOfLecture = numOfLecture; }

}
