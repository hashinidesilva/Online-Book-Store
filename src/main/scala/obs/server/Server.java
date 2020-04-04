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
import obs.controller.Controller;
import obs.model.Book;
import scala.jdk.javaapi.CollectionConverters;

public class Server {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/book", new Handler());
        server.setExecutor(null);
        server.start();
    }

    static class Handler implements HttpHandler{
        public void handle(HttpExchange t) throws IOException{
            String request=t.getRequestMethod();
            String response="";
            switch(request) {
                case "GET" :
                    String[] pathParam=t.getRequestURI().getPath().split("/");
                    if(pathParam.length==2) response = bookList();
                    else response = searchBook(pathParam[2],pathParam[3]);
                    break;
                case "POST":
                    response= addBook(t);
                    break;
            }
            t.getResponseHeaders().add("Content-type"," application/json; charset=utf-8");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static String bookList(){
        List<Book> bookList=CollectionConverters.asJava(Controller.getBookList());
        return objectToJson(bookList);
    }

    public static String searchBook(String param1,String param2){
        Iterable<Book> searchList=CollectionConverters.asJava(Controller.searchBook(param1.toLowerCase(),param2.toLowerCase()));
        return objectToJson(searchList);
    }

    public static String addBook(HttpExchange t){
        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), StandardCharsets.UTF_8);
        Stream<String> query = new BufferedReader(isr).lines();
        StringBuilder stringBuilder = new StringBuilder();
        query.forEach((s) -> stringBuilder.append(s).append("\n"));
        List<Book> b=jsonToObject(stringBuilder.toString());
        List<Book> newBooks=CollectionConverters.asJava(Controller.addBook(CollectionConverters.asScala(b)));
        return objectToJson(newBooks);
    }

    public static String objectToJson(Object list){
        return new Gson().toJson(list);
    }

    public static List<Book> jsonToObject(String response){
        Type listType = new TypeToken<List<Book>>(){}.getType();
        return new Gson().fromJson(response, listType);
    }
}