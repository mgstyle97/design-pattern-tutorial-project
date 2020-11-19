package io.wisoft.project;


import io.wisoft.project.course.CourseDAO;
import io.wisoft.project.course.CourseDTO;

public class Test {

    public static void main(String... args) {

        CourseDAO dao = new CourseDAO();

        CourseDTO dto = (CourseDTO) dao.select("C001");

        System.out.println(dto.getCredit());


    }

}
