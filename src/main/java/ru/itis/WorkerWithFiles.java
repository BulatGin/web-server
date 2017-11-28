package ru.itis;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Bulat Giniyatullin
 * 23 Ноябрь 2017
 */

public class WorkerWithFiles implements Runnable {
    private Socket socket;
    private final Path ROOT_PATH = Paths.get("SERVER_DIR");

    public WorkerWithFiles(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            String fileName = getFileName();
            System.out.println("Sending file: " + fileName);
            InputStream fileInputStream = new BufferedInputStream(
                    this.getClass().getClassLoader()
                            .getResourceAsStream(ROOT_PATH.resolve(fileName).toString())
            );
            fileInputStream.transferTo(socket.getOutputStream());
            System.out.println(String.format("File %s sended", fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileName() throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(
                socket.getInputStream());
        int c;
        StringBuilder stringBuilder = new StringBuilder();
        while ((c = inputStream.read()) != -1) {
            stringBuilder.append((char)c);
        }
        inputStream.close();
        return stringBuilder.toString();
    }
}
