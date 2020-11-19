package io.wisoft.project.singleton;

import io.wisoft.project.factory.UserDAOFactory;

public class UserDAOFactorySingleton {

    private static UserDAOFactory factory = null;

    private UserDAOFactorySingleton() {}

    public static UserDAOFactory getInstance() {
        if(factory == null) {
            factory = new UserDAOFactory();
        }

        return factory;
    }
}
