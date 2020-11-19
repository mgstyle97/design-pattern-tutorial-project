package io.wisoft.project.dao.dto.course;

import io.wisoft.project.dao.dto.DTO;

public class CourseDTO extends DTO {

    private String name;
    private int credit;

    public CourseDTO(String id, String name, int credit) {
        super(id);
        this.name = name;
        this.credit = credit;
    }

    public String getName() { return this.name; }

    public int getCredit() { return this.credit; }

    public void setName(String name) { this.name = name; }

    public void setCredit(int credit) { this.credit = credit; }

    @Override
    public String toString() {
        String str = super.toString();
        str += "\n과목명: " + this.name;
        str += "\n학점: " + this.credit;

        return str;
    }

}
