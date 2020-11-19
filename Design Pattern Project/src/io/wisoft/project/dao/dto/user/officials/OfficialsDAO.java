package io.wisoft.project.dao.dto.user.officials;

import io.wisoft.project.dao.dto.user.UserDAO;

public class OfficialsDAO extends UserDAO {

    @Override
    protected String getRelation() { return "officials"; }

}
