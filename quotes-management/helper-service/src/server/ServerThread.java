package server;

import server.app.RequestHandler;
import server.http.HttpMethod;
import server.http.Request;
import server.http.Response;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerThread implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public ServerThread(Socket client) {
        this.client = client;

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String requestLine = in.readLine();

            StringTokenizer stringTokenizer = new StringTokenizer(requestLine);
            String method = stringTokenizer.nextToken();
            String path = stringTokenizer.nextToken();

            System.out.println("\nHTTP request:\n");
            do {
                System.out.println(requestLine);
                requestLine = in.readLine();
            }
            while (!requestLine.trim().equals(""));

            Request request = new Request(HttpMethod.valueOf(method), path);
            RequestHandler requestHandler = new RequestHandler();
            Response response = requestHandler.handle(request);

            System.out.println("\nHTTP response:\n");
            System.out.println(response.getResponseString());

            out.println(response.getResponseString());
            out.flush();

            in.close();
            out.close();
            client.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
