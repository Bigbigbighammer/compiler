import command.executor.CommandExecutor;
import command.parse.SimpleCommandParser;
import ui.SocketIOHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AgendaServer {

    private final int port;
    private final AppContext appContext;
    private volatile boolean running = true;

    public AgendaServer(int port) {
        this.port = port;
        this.appContext = new AppContext();
    }

    public void start() {
        appContext.init();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Agenda Server started on port " + port);

            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                new Thread(new ClientHandler(clientSocket, appContext)).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    public void stop() {
        running = false;
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private final AppContext appContext;

        public ClientHandler(Socket socket, AppContext appContext) {
            this.socket = socket;
            this.appContext = appContext;
        }

        @Override
        public void run() {
            try (SocketIOHandler ioHandler = new SocketIOHandler(socket)) {
                CommandExecutor executor = appContext.createExecutor(ioHandler);
                executor.start();
            } catch (IOException e) {
                System.err.println("Client handler error: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    // ignore
                }
                System.out.println("Client disconnected: " + socket.getInetAddress());
            }
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number, using default: " + port);
            }
        }

        AgendaServer server = new AgendaServer(port);
        server.start();
    }
}
