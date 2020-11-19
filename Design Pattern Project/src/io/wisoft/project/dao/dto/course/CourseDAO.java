package io.wisoft.project.dao.dto.course;

import io.wisoft.project.dao.dto.PostgresqlAccess;
import io.wisoft.project.dao.dto.DAO;
import io.wisoft.project.dao.dto.DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDAO extends DAO {

    @Override
    protected String getValues(DTO dto) {

        String values = null;

        CourseDTO course = (CourseDTO) dto;

        values = "('" + course.getId() + "', '";
        values += course.getName() + "', '";
        values += course.getCredit() + "')";

        return values;

    }

    @Override
    protected String getAttribute() {
        return "(id, name, credit)";
    }

    @Override
    protected  DTO resultSetToDTO(ResultSet rs) throws SQLException {

        String course_no;
        String name;
        int credit;
        CourseDTO dto;

        course_no = rs.getString("id");
        name = rs.getString("name");
        credit = rs.getInt("credit");

        dto = new CourseDTO(course_no, name, credit);

        return dto;

    }

    @Override
    protected String getRelation() {
        return "course";
    }

    public void selectAll() {
        String query = "SELECT * FROM " + getRelation();

        try(Connection conn = PostgresqlAccess.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery()) {

            System.out.println("=================================");

            while(rs.next()) {
                CourseDTO course = (CourseDTO) resultSetToDTO(rs);
                System.out.println(course);
                System.out.println();
            }

            System.out.println("=================================");

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
    }

}
