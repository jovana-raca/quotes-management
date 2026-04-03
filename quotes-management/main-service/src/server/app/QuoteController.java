package server.app;

import server.Main;
import server.http.Request;
import server.http.response.HtmlResponse;
import server.http.response.RedirectResponse;
import server.http.response.Response;
import server.model.Quote;
import server.model.QuoteOfTheDay;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;

public class QuoteController extends Controller {

    public QuoteController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {
        Quote quoteOfTheDay = new QuoteOfTheDay().getQuoteOfTheDay();
        String quoteOfTheDayHtml = "<h2>Quote of The-Day</h2>" + "<p>" +quoteOfTheDay.quote + " -" + quoteOfTheDay.author + "</p>";

        StringBuilder savedQuotesHtml = new StringBuilder("<h2>Saved Quotes</h2>");
        for (Quote quote : Main.quotes) {
            savedQuotesHtml.append("<p>").append(quote.quote).append(" -").append(quote.author).append("</p>");
        }

        String htmlBody = "" +
                "<form method=\"POST\" action=\"/save-quote\">" +
                "<label>Quote: </label><input name=\"quote\" type=\"text\"><br><br>" +
                "<label>Author: </label><input name=\"author\" type=\"text\"><br><br>" +
                "<button>Submit</button>" +
                "</form>" +
                quoteOfTheDayHtml +
                savedQuotesHtml;
        String content = "<html><head><title>Odgovor servera</title></head>\n" + "<body>" + htmlBody + "</body></html>";

        HtmlResponse htmlResponse = new HtmlResponse(content);
        return htmlResponse;
    }

    @Override
    public Response doPost() {
        String body = request.getBody();

        try {
             String text = URLDecoder.decode(body.split("&")[0].split("=")[1], "UTF-8");
             String author = URLDecoder.decode(body.split("&")[1].split("=")[1], "UTF-8");

             Quote quote = new Quote(text, author);
             Main.quotes.add(quote);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RedirectResponse redirectResponse = new RedirectResponse("/quotes");
        return redirectResponse;
    }
}
