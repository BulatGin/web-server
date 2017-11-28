package ru.itis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Bulat Giniyatullin
 * 23 Ноябрь 2017
 */

public class Server implements Runnable {
    //default port
    private int port = 8080;
    private boolean isRunning;
    private ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("Server started");
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Port %d cannot be opened", port));
        }
        while (isRunning) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException("Can not open connection with client");
            }
            new Thread(new WorkerWithFiles(socket)).start();
        }
        System.out.println("Server stopped");
    }

    public void stop() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Server can not be closed");
        }
    }
}
