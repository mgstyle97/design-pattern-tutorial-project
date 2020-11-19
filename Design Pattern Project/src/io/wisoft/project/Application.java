package io.wisoft.project;

import io.wisoft.project.common.State;
import io.wisoft.project.factory.ServerFactory;
import io.wisoft.project.server.Server;
import io.wisoft.project.singleton.ServerFactorySingleton;
import io.wisoft.project.dao.dto.user.UserDTO;


public class Application {

    public static void main(String... args) {

        ServerFactory serverFactory = ServerFactorySingleton.getInstance();
        Server server = serverFactory.create(State.UNLOGGEDIN);
        UserDTO user = new UserDTO(State.UNLOGGEDIN);

        while (true) {
            user = server.showPage(user);

            if(user.getState() == State.EXIT) {
                break;
            }

            server = serverFactory.create(user.getState());

        }

    }

}
