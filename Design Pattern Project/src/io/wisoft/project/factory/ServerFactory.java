package io.wisoft.project.factory;

import io.wisoft.project.common.State;
import io.wisoft.project.server.*;

public class ServerFactory {

    private final Server studentServer = new StudentServer();
    private final Server professorServer = new ProfessorServer();
    private final Server officialsServer = new OfficialsServer();
    private final Server unLoggedInServer = new UnLoggedInServer();

    public Server create(State state) {
        switch (state) {
            case STUDENTLOGGEDIN:
                return studentServer;
            case PROFESSORLOGGEDIN:
                return professorServer;
            case OFFICIALSLOGGEDIN:
                return officialsServer;
            case UNLOGGEDIN:
            default:
                return unLoggedInServer;

        }
    }
}
