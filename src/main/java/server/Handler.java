package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import obs.common.Utility;
import obs.controller.Controller;
import scala.Tuple2;

public class Handler implements HttpHandler {

    public void  handle(HttpExchange httpExchange) throws IOException {
        String request=httpExchange.getRequestMethod();
        URI uri = httpExchange.getRequestURI();
        Controller controller=new Controller();
        //RequestBody
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
        Stream<String> query = new BufferedReader(isr).lines();
        StringBuilder stringBuilder = new StringBuilder();
        query.forEach((s) -> stringBuilder.append(s).append("\n"));
        String requestBody=stringBuilder.toString();
        //Get response and status
        Tuple2<String, Integer> tuple2=controller.getResponse(Utility.parseUri(uri),request,requestBody);
        String response=tuple2._1;
        int status=tuple2._2;
        httpExchange.getResponseHeaders().add("Content-type", " application/json; charset=utf-8");
        httpExchange.sendResponseHeaders(status, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

