package server;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import obs.server.Handler;

public class Server {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/books", new Handler());
        server.setExecutor(null);
        server.start();
    }
}