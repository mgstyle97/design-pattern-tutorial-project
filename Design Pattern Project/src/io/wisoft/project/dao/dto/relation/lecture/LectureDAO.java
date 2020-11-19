package io.wisoft.project.dao.dto.relation.lecture;

import io.wisoft.project.dao.dto.PostgresqlAccess;
import io.wisoft.project.dao.dto.DAO;
import io.wisoft.project.dao.dto.DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class LectureDAO extends DAO{

    @Override
    protected String getWHERESection(String professorId) { return " WHERE professor_id = '" + professorId + "'"; }

    @Override
    protected String getValues(DTO dto) {

        String values = null;

        LectureDTO lecture = (LectureDTO) dto;

        values = "('" + lecture.getId() + "', '";
        values += lecture.getCourseId() + "', '";
        values += lecture.getDayOfWeek() + "', '";
        values += lecture.getTime() + "', ";
        values += lecture.getCredit() + ")";

        return values;

    }

    @Override
    protected String getAttribute() { return "(professor_id, course_id, day_of_week, time, credit)"; }

    @Override
    protected DTO resultSetToDTO(ResultSet rs) throws SQLException {

        String professorId;
        String courseId;
        String dayOfWeek;
        LectureDTO.Time time;
        int credit;
        LectureDTO dto;

        professorId = rs.getString("professor_id");
        courseId = rs.getString("course_id");
        dayOfWeek = rs.getString("day_of_week");
        time = LectureDTO.Time.valueOf(rs.getString("time"));
        credit = rs.getInt("credit");

        dto = new LectureDTO(professorId, courseId, dayOfWeek, time, credit);

        return dto;

    }

    @Override
    protected String getRelation() { return "lecture"; }

    @Override
    public void insert(DTO dto) {

        String query = "INSERT INTO " + getRelation() + getAttribute();
        query += " VALUES " + getValues(dto);

        try(Connection conn = PostgresqlAccess.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }

    }

    @Override
    public void delete(DTO dto) {

        LectureDTO lecture = (LectureDTO) dto;
        String query = "DELETE FROM " + getRelation() + getWHERESection(lecture.getId()) + " AND course_id = ?";

        try(Connection conn = PostgresqlAccess.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, lecture.getCourseId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
    }

    public void selectAll() {

        String query = "SELECT * FROM " + getRelation();

        try(Connection conn = PostgresqlAccess.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery()) {

            while(rs.next()) {

                LectureDTO lecture = (LectureDTO) resultSetToDTO(rs);

                System.out.println(lecture);
                System.out.println();

            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
    }

    public DTO select(String professorId, String courseId) {

        LectureDTO lecture = null;
        String query = "SELECT * FROM " + getRelation() + getWHERESection(professorId);
        query += " AND course_id = ?";

        try(Connection conn = PostgresqlAccess.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, courseId);

            try(ResultSet rs = pstmt.executeQuery()) {

                if(rs.next()) {
                    lecture = (LectureDTO) resultSetToDTO(rs);
                }

            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }

        return lecture;

    }

    public HashMap<String, LectureDTO> getLectureList(String professorId) {

        HashMap<String, LectureDTO> lectureMap = new HashMap<>();
        String query = "SELECT * FROM " + getRelation() + getWHERESection(professorId);

        try(Connection conn = PostgresqlAccess.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery()) {

                while(rs.next()) {
                    LectureDTO dto = (LectureDTO) resultSetToDTO(rs);
                    String courseId = dto.getCourseId();
                    lectureMap.put(courseId, dto);
                }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }

        return lectureMap;

    }

}
