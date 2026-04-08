import command.executor.CommandExecutor;
import command.parse.SimpleCommandParser;
import ui.SocketIOHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 议程服务服务端。
 * <p>
 * 监听指定端口，接受客户端连接，为每个客户端创建独立的会话。
 * 支持多客户端同时连接。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class AgendaServer {

    /** 监听端口 */
    private final int port;

    /** 应用程序上下文 */
    private final AppContext appContext;

    /** 服务运行标志 */
    private volatile boolean running = true;

    /**
     * 构造服务端。
     *
     * @param port 监听端口
     */
    public AgendaServer(int port) {
        this.port = port;
        this.appContext = new AppContext();
    }

    /**
     * 启动服务端。
     * <p>
     * 初始化上下文，监听端口，接受客户端连接。
     * </p>
     */
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

    /**
     * 停止服务端。
     */
    public void stop() {
        running = false;
    }

    /**
     * 客户端连接处理器。
     * <p>
     * 为每个客户端连接创建独立的命令执行器。
     * </p>
     */
    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private final AppContext appContext;

        /**
         * 构造客户端处理器。
         *
         * @param socket     客户端 Socket
         * @param appContext 应用程序上下文
         */
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

    /**
     * 主方法。
     *
     * @param args 命令行参数，第一个参数为端口号（可选，默认 8080）
     */
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
