package io.wisoft.project.dao.dto.user.student;

import io.wisoft.project.dao.dto.user.UserDAO;

public class StudentDAO extends UserDAO {

    @Override
    protected String getRelation() {
        return "student";
    }

}
