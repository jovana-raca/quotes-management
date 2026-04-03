package server.app;

import com.google.gson.Gson;
import server.Main;
import server.http.Request;
import server.http.Response;
import server.model.Quote;

import java.util.List;
import java.util.Random;

public class QuoteController extends Controller {

    public QuoteController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {
        Random random = new Random();
        Quote quote = Main.quotes.get(random.nextInt(Main.quotes.size()));

        Gson gson = new Gson();
        String json = gson.toJson(quote);

        Response response = new Response(json);
        return response;
    }
}
