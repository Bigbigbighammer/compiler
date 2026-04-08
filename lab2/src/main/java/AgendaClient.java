import ui.SocketIOHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 议程服务客户端。
 * <p>
 * 连接到服务端，发送命令并接收结果。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class AgendaClient {

    /** 服务端主机地址 */
    private final String host;

    /** 服务端端口 */
    private final int port;

    /**
     * 构造客户端。
     *
     * @param host 服务端主机地址
     * @param port 服务端端口
     */
    public AgendaClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 启动客户端。
     * <p>
     * 连接服务端，启动接收线程，读取用户输入并发送命令。
     * </p>
     */
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

    /**
     * 主方法。
     *
     * @param args 命令行参数，格式：&lt;host&gt; &lt;port&gt;
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java AgendaClient <host> <port>");
            System.out.println("Example: java AgendaClient localhost 8080");
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
