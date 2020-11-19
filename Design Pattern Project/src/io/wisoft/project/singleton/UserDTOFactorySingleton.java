package io.wisoft.project.singleton;

import io.wisoft.project.factory.UserDTOFactory;

public class UserDTOFactorySingleton {

    private static UserDTOFactory factory = null;

    private UserDTOFactorySingleton() {}

    public static UserDTOFactory getInstance() {
        if(factory == null) {
            factory = new UserDTOFactory();
        }

        return factory;
    }

}
