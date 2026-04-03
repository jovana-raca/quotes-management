package server.http;

public class Response {

    private final String json;

    public Response(String json) {
        this.json = json;
    }

    public String getResponseString(){
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" + json;
        return response;
    }
}
