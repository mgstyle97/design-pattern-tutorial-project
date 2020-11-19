package io.wisoft.project.dao.dto.relation.register;

import io.wisoft.project.dao.dto.DTO;

public class RegisterDTO extends DTO {

    private String professorId, courseId;

    public RegisterDTO(String studentId, String professorId, String courseId) {
        super(studentId);
        this.professorId = professorId;
        this.courseId = courseId;
    }

    public String getProfessorId() { return this.professorId; }

    public String getCourseId() { return this.courseId; }

    public void setProfessorId(String professorId) { this.professorId = professorId; }

    public void setCourseId(String courseId) { this.courseId = courseId; }


}
