package io.wisoft.project.singleton;

import java.util.Scanner;

public class ScannerSingleton {

    private static Scanner scanner = null;

    private ScannerSingleton() {}

    public static Scanner getInstance() {
        if(scanner == null) {
            scanner = new Scanner(System.in);
        }

        return scanner;
    }

}
