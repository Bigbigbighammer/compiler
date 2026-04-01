import ui.IOHandler;
import ui.SocketIOHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AgendaClient {

    private final String host;
    private final int port;

    public AgendaClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try (Socket socket = new Socket(host, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to Agenda Server at " + host + ":" + port);

            // Start a thread to receive messages from server
            Thread receiver = new Thread(() -> {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        // Print prompt after each server message (except Goodbye)
                        if (!"Goodbye!".equals(line)) {
                            System.out.print("$ ");
                        }
                    }
                } catch (IOException e) {
                    // Connection closed
                }
            });
            receiver.setDaemon(true);
            receiver.start();

            // Read input from console and send to server
            String input;
            while ((input = consoleReader.readLine()) != null) {
                writer.println(input);
                if ("quit".equalsIgnoreCase(input.trim())) {
                    break;
                }
            }

            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java AgendaClient <host> <port>");
            System.out.println("Example: java AgendaClient localhost 8888");
            return;
        }

        String host = args[0];
        int port;
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid port number");
            return;
        }

        AgendaClient client = new AgendaClient(host, port);
        client.start();
    }
}
