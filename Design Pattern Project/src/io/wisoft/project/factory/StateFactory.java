package io.wisoft.project.factory;

import io.wisoft.project.common.Relation;
import io.wisoft.project.common.State;

public class StateFactory {
    public static State create(Relation relation) {
        switch (relation) {
            case STUDENT:
                return State.STUDENTLOGGEDIN;
            case OFFICIALS:
                return State.OFFICIALSLOGGEDIN;
            case PROFESSOR:
                return State.PROFESSORLOGGEDIN;
            default:
                return State.UNLOGGEDIN;
        }
    }
}
