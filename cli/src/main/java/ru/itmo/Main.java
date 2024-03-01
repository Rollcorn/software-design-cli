package ru.itmo;

import java.io.IOException;

public class Main {
    static ICli cli = new ICliImpl();

    public static void main(String[] args) {
        try {
            cli.run();
        } catch (IOException e) {
            System.out.println("Execution failed: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ignore) {
            System.out.println("sorry" + ignore.getMessage());
        }
    }
}
