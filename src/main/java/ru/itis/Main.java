package ru.itis;

/**
 * @author Bulat Giniyatullin
 * 23 Ноябрь 2017
 */

public class Main {
    static final int PORT = 8080;

    public static void main(String[] args) {
        new Thread(new Server(PORT)).start();
    }
}
