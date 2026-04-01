package ui;

import java.util.Scanner;

public class ConsoleIOHandler implements IOHandler {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        System.out.print("$ ");
        return scanner.nextLine();
    }

    @Override
    public void writeLine(String message) {
        System.out.println(message);
    }
}
