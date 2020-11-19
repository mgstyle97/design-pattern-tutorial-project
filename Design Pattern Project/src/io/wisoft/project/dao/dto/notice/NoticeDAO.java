package io.wisoft.project.dao.dto.notice;

import io.wisoft.project.dao.dto.PostgresqlAccess;
import io.wisoft.project.dao.dto.DAO;
import io.wisoft.project.dao.dto.DTO;
import io.wisoft.project.dao.dto.user.UserDTO;
import io.wisoft.project.dao.dto.user.officials.OfficialsDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoticeDAO extends DAO {

    private final OfficialsDAO officialsDAO = new OfficialsDAO();


    @Override
    protected String getWHERESection(String notice_no) {
        return " WHERE notice_no = " + notice_no;
    }

    @Override
    protected String getValues(DTO dto) {

        String values = null;

        NoticeDTO notice = (NoticeDTO) dto;

        values = "('" + notice.getTitle() + "', '";
        values += notice.getContent() + "', '";
        values += notice.getWriter() + "')";

        return values;

    }

    @Override
    protected String getAttribute() { return "(title, content, writer)"; }

    @Override
    protected DTO resultSetToDTO(ResultSet rs) throws SQLException {

        String id;
        String title;
        String content;
        String writer;
        NoticeDTO dto;

        id = rs.getString("notice_no");
        title = rs.getString("title");
        content = rs.getString("content");
        writer = rs.getString("writer");

        writer = ((UserDTO)officialsDAO.select(writer)).getName();

        dto = new NoticeDTO(id, title, content, writer);

        return dto;

    }

    @Override
    protected String getRelation() { return "notice"; }

    @Override
    public boolean isContainData(String title) {

        boolean result = false;
        String query = "SELECT CASE WHEN COUNT(*)=1 THEN 'true' ELSE 'false' END AS result ";
        query += "FROM " + getRelation() + " WHERE title = ?";

        try(Connection conn = PostgresqlAccess.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, title);

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

    public void selectAll() {

        String query = "SELECT * FROM " + getRelation() + " ORDER BY notice_no";

        try(Connection conn = PostgresqlAccess.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery()) {

            while(rs.next()) {

                String notice_no = rs.getString("notice_no");
                String title = rs.getString("title");
                String writer = rs.getString("writer");

                writer = ((UserDTO)officialsDAO.select(writer)).getName();

                System.out.println("번호: " + notice_no);
                System.out.println("제목: " + title);
                System.out.println("작성자: " + writer);
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }

    }

}
