package ru.itmo;

public class Main {
    static ICli cli = new ICliImpl();

    public static void main(String[] args) {
        try {
            cli.run();
        } catch (Exception ignore) {
            System.out.println("sorry");
        }
    }
}
