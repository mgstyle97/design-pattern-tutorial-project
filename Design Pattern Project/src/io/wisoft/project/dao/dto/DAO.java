package io.wisoft.project.dao.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DAO {

    public DTO select(String id) {

        DTO dto = null;

        if(isContainData(id)) {

            String query = "SELECT * FROM " + getRelation() + getWHERESection(id);

            try(Connection conn = PostgresqlAccess.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

                if(rs.next()) {
                    dto = resultSetToDTO(rs);
                }

            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
            }

        } else {
            System.out.println("해당 데이터가 존재하지 않습니다.");
        }

        return dto;

    }

    public void insert(DTO dto) {

        if(!isContainData(dto.getId())) {

            String query = "INSERT INTO " + getRelation() + getAttribute() + " VALUES " + getValues(dto);

            try(Connection conn = PostgresqlAccess.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
            }

        } else {
            System.out.println("해당 데이터가 이미 존재합니다.");
        }

    }

    public void update(String attribute, String newData, DTO dto) {

        if(isContainData(dto.getId())) {

            String query = "UPDATE " + getRelation() + " SET " + attribute + " = ? ";
            query += getWHERESection(dto.getId());

            try(Connection conn = PostgresqlAccess.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, newData);

                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
            }

        } else {
            System.out.println("해당 데이터가 존재하지 않습니다.");
        }

    }

    public void delete(DTO dto) {

        if(isContainData(dto.getId())) {

            String query = "DELETE FROM " + getRelation() + getWHERESection(dto.getId());

            try(Connection conn = PostgresqlAccess.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
            }

        } else {
            System.out.println("해당 데이터가 존재하지 않습니다.");
        }

    }

    public boolean isContainData(String id) {

        boolean result = false;
        String query = "SELECT CASE WHEN COUNT(*)=1 THEN 'true' ELSE 'false' END AS result ";
        query += "FROM " + getRelation();
        query += getWHERESection(id);

        try(Connection conn = PostgresqlAccess.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery()) {

            if(rs.next()) {
                result = Boolean.parseBoolean(rs.getString("result"));
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }

        return result;
    }

    protected String getWHERESection(String id) {
        return " WHERE id = '" + id + "'";
    }

    protected abstract String getValues(DTO dto);

    protected abstract String getAttribute();

    protected abstract DTO resultSetToDTO(ResultSet rs) throws SQLException;

    protected abstract String getRelation();

}
