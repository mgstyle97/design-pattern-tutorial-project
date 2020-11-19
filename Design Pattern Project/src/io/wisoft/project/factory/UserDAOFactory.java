package io.wisoft.project.factory;

import io.wisoft.project.common.Relation;
import io.wisoft.project.common.State;
import io.wisoft.project.dao.dto.DAO;
import io.wisoft.project.dao.dto.user.officials.OfficialsDAO;
import io.wisoft.project.dao.dto.user.professor.ProfessorDAO;
import io.wisoft.project.dao.dto.user.student.StudentDAO;

public class UserDAOFactory {

    private final DAO studentDAO = new StudentDAO();
    private final DAO professorDAO = new ProfessorDAO();
    private final DAO officialsDAO = new OfficialsDAO();

    public DAO create(Relation title) {
        switch (title) {
            case STUDENT:
                return studentDAO;
            case PROFESSOR:
                return professorDAO;
            case OFFICIALS:
                return officialsDAO;
            default:
                return null;
        }
    }

    public DAO create(State state) {
        switch (state) {
            case STUDENTLOGGEDIN:
                return studentDAO;
            case PROFESSORLOGGEDIN:
                return professorDAO;
            case OFFICIALSLOGGEDIN:
                return officialsDAO;
            default:
                return null;
        }
    }
}
