package server.app;

import server.http.HttpMethod;
import server.http.Request;
import server.http.Response;

public class RequestHandler {

    public Response handle(Request request) throws Exception {
        if (request.getPath().equals("/quote-of-the-day") && request.getHttpMethod().equals(HttpMethod.GET)){
            return new QuoteController(request).doGet();
        }
        throw new Exception("Page: " + request.getPath() + ". Method: " + request.getHttpMethod() + " not found!");
    }
}
