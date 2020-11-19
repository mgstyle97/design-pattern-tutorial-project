package io.wisoft.project.singleton;

import io.wisoft.project.factory.ServerFactory;

public class ServerFactorySingleton {

    private static ServerFactory serverFactory = null;

    private ServerFactorySingleton() {}

    public static ServerFactory getInstance() {
        if(serverFactory == null) {
            serverFactory = new ServerFactory();
        }

        return serverFactory;
    }
}
