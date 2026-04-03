package server;

import server.app.RequestHandler;
import server.http.HttpMethod;
import server.http.Request;
import server.http.response.Response;

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

            int contentLength = 0;

            System.out.println("\nHTTP request:\n");
            do {
                System.out.println(requestLine);

                if (requestLine.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(requestLine.split(":")[1].trim());
                }

                requestLine = in.readLine();
            }
            while (!requestLine.trim().equals(""));

            String body = "";
            if (contentLength > 0){
                char[] buffer = new char[contentLength];
                in.read(buffer);
                body = new String(buffer);
            }

            Request request = null;
            if (method.equals(HttpMethod.POST.toString())){
                request = new Request(HttpMethod.valueOf(method), path, body);
            }
            else if (method.equals(HttpMethod.GET.toString())){
                request = new Request(HttpMethod.valueOf(method), path);
            }
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
