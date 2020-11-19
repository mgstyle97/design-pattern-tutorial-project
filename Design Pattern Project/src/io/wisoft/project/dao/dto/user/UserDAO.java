package io.wisoft.project.dao.dto.user;

import io.wisoft.project.dao.dto.DAO;
import io.wisoft.project.dao.dto.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDAO extends DAO {

    @Override
    protected String getValues(DTO dto) {

        String values = null;

        UserDTO user = (UserDTO) dto;

        values = "('" + user.getId() + "', '";
        values += user.getPassword() + "', '";
        values += user.getName() + "', '";
        values += user.getDept() + "')";

        return values;

    }

    @Override
    protected String getAttribute() {
        return "(id, password, name, dept)";
    }

    @Override
    protected DTO resultSetToDTO(ResultSet rs) throws SQLException {

        String id;
        String name;
        String password;
        String dept;
        UserDTO dto;

        id = rs.getString("id");
        password = rs.getString("password");
        name = rs.getString("name");
        dept = rs.getString("dept");

        dto = new UserDTO(id, password, name, dept);

        return dto;

    }

    @Override
    protected abstract String getRelation();

}
