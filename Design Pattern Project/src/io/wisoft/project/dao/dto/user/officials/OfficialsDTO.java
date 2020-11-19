package io.wisoft.project.dao.dto.user.officials;

import io.wisoft.project.dao.dto.user.UserDTO;

public class OfficialsDTO extends UserDTO {

    private int numOfNotice;

    public OfficialsDTO(String id, String password, String name, String dept) {
        super(id, password, name, dept);
    }

    public int getNumOfNotice() {
        return numOfNotice;
    }

    public void setNumOfNotice(int numOfNotice) {
        this.numOfNotice = numOfNotice;
    }
}
