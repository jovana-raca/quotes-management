package server.model;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class QuoteOfTheDay {

    public Quote getQuoteOfTheDay() {
        try {
            Socket socket = new Socket("localhost", 8081);
            PrintWriter socketOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            socketOut.println("GET /quote-of-the-day HTTP/1.1");
            socketOut.println("Host: localhost:8081");
            socketOut.println("");
            socketOut.flush();

            String requestLine = socketIn.readLine();
            do {
                requestLine = socketIn.readLine();
            }
            while (requestLine!= null && !requestLine.trim().equals(""));

            String responseLine = socketIn.readLine();
            String response = responseLine.trim();

            Gson gson = new Gson();
            Quote quote = gson.fromJson(response, Quote.class);

            return quote;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
