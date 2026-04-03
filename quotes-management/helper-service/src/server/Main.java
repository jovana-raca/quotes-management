package server;

import server.model.Quote;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {

    public static final int PORT = 8081;
    public static List<Quote> quotes = List.of(
            new Quote("Citat", "Ja")
    );

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("Waiting for connections...");

                Socket socket = serverSocket.accept();
                System.out.println("Accepted connection!");

                Thread serverThread = new Thread(new ServerThread(socket));
                serverThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
