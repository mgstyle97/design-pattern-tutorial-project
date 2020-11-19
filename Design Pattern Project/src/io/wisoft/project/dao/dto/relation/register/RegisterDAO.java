package io.wisoft.project.dao.dto.relation.register;

import io.wisoft.project.dao.dto.PostgresqlAccess;
import io.wisoft.project.dao.dto.DAO;
import io.wisoft.project.dao.dto.DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterDAO extends DAO {

    @Override
    protected String getWHERESection(String studentId) { return " WHERE student_id = '" + studentId + "'"; }

    @Override
    protected String getValues(DTO dto) {

        String values = null;

        RegisterDTO register = (RegisterDTO) dto;

        values = "('" + register.getId() + "', '";
        values += register.getProfessorId() + "', '";
        values += register.getCourseId() + "')";

        return values;

    }

    @Override
    protected String getAttribute() { return "(student_id, professor_id, course_id)"; }

    @Override
    protected DTO resultSetToDTO(ResultSet rs) throws SQLException {

        String studentId;
        String professorId;
        String courseId;
        io.wisoft.project.dao.dto.relation.register.RegisterDTO dto;

        studentId = rs.getString("student_id");
        professorId = rs.getString("professor_id");
        courseId = rs.getString("course_id");

        dto = new RegisterDTO(studentId, professorId, courseId);

        return dto;

    }

    @Override
    protected String getRelation() { return "register"; }

    @Override
    public void insert(DTO dto) {

        RegisterDTO register = (RegisterDTO) dto;

        if(!isContainData(register.getId(), register.getProfessorId(), register.getCourseId())) {
            String query = "INSERT INTO " + getRelation() + getAttribute();
            query += " VALUES " + getValues(dto);

            try(Connection conn = PostgresqlAccess.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
            }
        } else {
            System.out.println("해당 강의는 이미 존재합니다.");
            System.out.println();
        }

    }

    @Override
    public void delete(DTO dto) {

        RegisterDTO register = (RegisterDTO) dto;

        if(isContainData(register.getId(), register.getProfessorId(), register.getCourseId())) {
            String query = "DELETE FROM " + getRelation() + getWHERESection(register.getId());
            query += " AND professor_id = ?";
            query += " AND course_id = ?";

            try(Connection conn = PostgresqlAccess.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, register.getProfessorId());
                pstmt.setString(2, register.getCourseId());

                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
            }
        } else {
            System.out.println("존재하지 않는 강의입니다.");
            System.out.println();
        }

    }

    public boolean isContainData(String studentId, String professorId, String courseId) {

        boolean result = false;
        String query = "SELECT CASE WHEN COUNT(*)=1 THEN 'true' ELSE 'false' END AS result";
        query += " FROM " + getRelation() + getWHERESection(studentId);
        query += " AND professor_id = ?";
        query += " AND course_id = ?";

        try(Connection conn = PostgresqlAccess.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, professorId);
            pstmt.setString(2, courseId);

            try(ResultSet rs = pstmt.executeQuery()) {

                if(rs.next()) {
                    result = Boolean.parseBoolean(rs.getString("result"));
                }

            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }

        return result;

    }

    public List<RegisterDTO> getLectureList(String studentId) {

        List<RegisterDTO> lectureList = new ArrayList<>();
        String query = "SELECT * FROM " + getRelation() + getWHERESection(studentId);

        try(Connection conn = PostgresqlAccess.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery()) {

            while(rs.next()) {

                RegisterDTO  dto = (RegisterDTO) resultSetToDTO(rs);
                lectureList.add(dto);

            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }

        return lectureList;

    }

}
