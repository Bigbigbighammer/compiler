package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Socket 输入输出处理器。
 * <p>
 * 通过 Socket 连接实现 {@link IOHandler} 接口，
 * 适用于远程客户端交互模式。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class SocketIOHandler implements IOHandler, AutoCloseable {

    /** 读取器，用于读取 Socket 输入流 */
    private final BufferedReader reader;

    /** 写入器，用于写入 Socket 输出流 */
    private final PrintWriter writer;

    /**
     * 构造 Socket 输入输出处理器。
     *
     * @param socket Socket 连接
     * @throws IOException 如果获取输入输出流失败
     */
    public SocketIOHandler(Socket socket) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLine(String message) {
        writer.println(message);
    }

    /**
     * 关闭读写器。
     */
    @Override
    public void close() {
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            // ignore
        }
    }
}
