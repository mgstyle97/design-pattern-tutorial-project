package io.wisoft.project.factory;

import io.wisoft.project.dao.dto.user.UserDTO;
import io.wisoft.project.common.Relation;
import io.wisoft.project.dao.dto.user.officials.OfficialsDTO;
import io.wisoft.project.dao.dto.user.professor.ProfessorDTO;
import io.wisoft.project.dao.dto.user.student.StudentDTO;

public class UserDTOFactory {

    public UserDTO create(Relation relation, UserDTO user) {
        switch (relation) {
            case STUDENT:
                return new StudentDTO(user.getId(), user.getPassword(), user.getName(), user.getDept());
            case PROFESSOR:
                return new ProfessorDTO(user.getId(), user.getPassword(), user.getName(), user.getDept());
            case OFFICIALS:
                return new OfficialsDTO(user.getId(), user.getPassword(), user.getName(), user.getDept());
            default:
                return user;
        }
    }

}
