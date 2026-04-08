package ui;

import java.util.Scanner;

/**
 * 控制台输入输出处理器。
 * <p>
 * 通过标准输入输出实现 {@link IOHandler} 接口，
 * 适用于本地命令行交互模式。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class ConsoleIOHandler implements IOHandler {

    /** 扫描器，用于读取标准输入 */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * {@inheritDoc}
     * <p>
     * 显示提示符 "$ " 后等待用户输入。
     * </p>
     */
    @Override
    public String readLine() {
        System.out.print("$ ");
        return scanner.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLine(String message) {
        System.out.println(message);
    }
}
