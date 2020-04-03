package obs.server;

import java.io.*;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import obs.Book;
import obs.Controller;
import scala.jdk.javaapi.CollectionConverters;

public class server {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/list", new GetHandler());
        server.createContext("/add", new PostHandler());
        server.createContext("/search", new SearchHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class GetHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println(t.getRequestMethod());
            java.util.List<Book> bookList=CollectionConverters.asJava(Controller.getBookList());
            String response = objectToJson(bookList);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class PostHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            Stream<String> query = br.lines();
            StringBuilder stringBuilder = new StringBuilder();
            query.forEach((s) -> stringBuilder.append(s).append("\n"));
            String book= stringBuilder.toString();
            Book newBook=Controller.addBook(jsonToObject(book));
            String response= objectToJson(newBook);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class SearchHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String params=t.getRequestURI().getQuery();
            String[] paramList = params.split("=");
            java.util.List<Book> searchList=CollectionConverters.asJava(Controller.searchBook(paramList[0],paramList[1]));
            String response = objectToJson(searchList);
//            System.out.println(t.getRequestURI().getPath());
//            String response="";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static String objectToJson(Object list){
        Gson gson=new Gson();
        return gson.toJson(list);
    }

    public static Book jsonToObject(String response){
        return new Gson().fromJson(response,Book.class);
//        Type listType = new TypeToken<List<Book>>(){}.getType();
//        List<Book> myModelList = gson.fromJson(response, listType);
    }


}
