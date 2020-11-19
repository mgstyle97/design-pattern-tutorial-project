package io.wisoft.project.dao.dto.user.professor;

import io.wisoft.project.dao.dto.user.UserDAO;

public class ProfessorDAO extends UserDAO {

    @Override
    protected String getRelation() {
        return "professor";
    }

}
