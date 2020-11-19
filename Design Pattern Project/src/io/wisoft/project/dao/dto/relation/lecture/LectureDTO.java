package io.wisoft.project.dao.dto.relation.lecture;

import io.wisoft.project.dao.dto.DTO;
import io.wisoft.project.dao.dto.user.UserDTO;
import io.wisoft.project.course.CourseDAO;
import io.wisoft.project.course.CourseDTO;
import io.wisoft.project.dao.dto.user.professor.ProfessorDAO;

public class LectureDTO extends DTO {

    public enum Time {
        AM,
        PM
    }

    private String courseId;
    private String dayOfWeek;
    private Time time;
    private int credit;

    public LectureDTO(String professorId, String courseId, String dayOfWeek, Time time, int credit) {
        super(professorId);
        this.courseId = courseId;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.credit = credit;
    }

    public String getCourseId() { return this.courseId; }

    public String getDayOfWeek() { return this.dayOfWeek; }

    public Time getTime() { return this.time; }

    public int getCredit() { return this.credit; }

    public void setCourseId(String courseId) { this.courseId = courseId; }

    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public void setTime(Time time) { this.time = time; }

    public void setCredit() { this.credit = credit; }

    @Override
    public String toString() {
        ProfessorDAO professorDAO = new ProfessorDAO();
        CourseDAO courseDAO = new CourseDAO();
        UserDTO professorDTO = (UserDTO) professorDAO.select(getId());
        CourseDTO courseDTO = (CourseDTO) courseDAO.select(this.courseId);

        String str = getId() + "\t" + professorDTO.getName();
        str += "\n" + this.courseId + "\t" + courseDTO.getName();
        str += "\n" + this.dayOfWeek + "\t" + this.time + "\t" + this.credit;

        return str;
    }

}
