import command.executor.CommandExecutor;
import command.parse.SimpleCommandParser;
import command.registry.CommandRegistry;
import domain.Meeting;
import mapper.MeetingMapper;
import mapper.UserMapper;
import mapper.impl.DefaultMeetingMapper;
import mapper.impl.DefaultUserMapper;
import service.MeetingService;
import service.UserService;
import ui.IOHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        // Setup
        UserMapper userMapper = new DefaultUserMapper();
        MeetingMapper meetingMapper = new DefaultMeetingMapper();
        UserService userService = new UserService(userMapper);
        MeetingService meetingService = new MeetingService(meetingMapper, userService);
        CommandRegistry registry = new CommandRegistry();
        IOHandler io = new TestIOHandler();

        // Register commands
        registry.registerCommand(command.enums.CommandType.REGISTER,
                new command.impl.RegisterCommand(userService));
        registry.registerCommand(command.enums.CommandType.ADD,
                new command.impl.AddMeetingCommand(userService, meetingService));
        registry.registerCommand(command.enums.CommandType.QUERY,
                new command.impl.QueryMeetingCommand(userService, meetingService));
        registry.registerCommand(command.enums.CommandType.DELETE,
                new command.impl.DeleteCommand(userService, meetingService));
        registry.registerCommand(command.enums.CommandType.CLEAR,
                new command.impl.ClearCommand(userService, meetingService));
        registry.registerCommand(command.enums.CommandType.QUIT,
                new command.impl.QuitCommand());

        CommandExecutor executor = new CommandExecutor(registry, new SimpleCommandParser(), io);

        // Register batch command with executor
        registry.registerCommand(command.enums.CommandType.BATCH,
                new command.impl.BatchCommand(executor));

        // Test cases
        System.out.println("========== Agenda Service Test ==========\n");

        // Test 1: Register users
        System.out.println("=== Test 1: Register users ===");
        execute(executor, "register alice 123456");
        execute(executor, "register bob password");
        execute(executor, "register alice 123456"); // Duplicate user
        System.out.println();

        // Test 2: Add meetings
        System.out.println("=== Test 2: Add meetings ===");
        execute(executor, "add alice 123456 bob 2024-01-01-10:00 2024-01-01-11:00 Project Meeting");
        execute(executor, "add alice 123456 bob 2024-01-01-14:00 2024-01-01-15:00 Code Review");
        execute(executor, "add bob password alice 2024-01-02-09:00 2024-01-02-10:00 Team Sync");
        System.out.println();

        // Test 3: Add meeting with conflict
        System.out.println("=== Test 3: Add meeting with conflict ===");
        execute(executor, "add alice 123456 bob 2024-01-01-10:30 2024-01-01-11:30 Conflict Test");
        System.out.println();

        // Test 4: Query meetings
        System.out.println("=== Test 4: Query meetings ===");
        execute(executor, "query alice 123456 2024-01-01-00:00 2024-01-02-00:00");
        System.out.println();

        // Test 5: Invalid time format
        System.out.println("=== Test 5: Invalid time format ===");
        execute(executor, "add alice 123456 bob 2024-01-01 10:00 2024-01-01 11:00 Test");
        System.out.println();

        // Test 6: Invalid arguments
        System.out.println("=== Test 6: Invalid arguments ===");
        execute(executor, "register onlyname");
        execute(executor, "add alice 123456 bob");
        System.out.println();

        // Test 7: Authentication failed
        System.out.println("=== Test 7: Authentication failed ===");
        execute(executor, "query alice wrongpassword 2024-01-01-00:00 2024-01-02-00:00");
        System.out.println();

        // Test 8: Delete meeting
        System.out.println("=== Test 8: Delete meeting ===");
        execute(executor, "delete alice 123456 1");
        execute(executor, "query alice 123456 2024-01-01-00:00 2024-01-03-00:00");
        System.out.println();

        // Test 9: Clear meetings
        System.out.println("=== Test 9: Clear meetings ===");
        execute(executor, "clear bob password");
        execute(executor, "query bob password 2024-01-01-00:00 2024-01-03-00:00");
        System.out.println();

        // Test 10: Batch command
        System.out.println("=== Test 10: Batch command ===");
        createBatchFile();
        execute(executor, "batch batch_commands.txt");
        System.out.println();

        System.out.println("========== Test Completed ==========");
    }

    private static void createBatchFile() {
        try (FileWriter writer = new FileWriter("batch_commands.txt")) {
            writer.write("register charlie pass123\n");
            writer.write("add charlie pass123 alice 2024-03-01-09:00 2024-03-01-10:00 Weekly Standup\n");
            writer.write("query charlie pass123 2024-03-01-00:00 2024-03-02-00:00\n");
            writer.write("delete charlie pass123 4\n");
            writer.write("clear charlie pass123\n");
        } catch (IOException e) {
            System.out.println("Failed to create batch file: " + e.getMessage());
        }
    }

    private static void execute(CommandExecutor executor, String input) {
        System.out.println("$ " + input);
        String result = executor.executeLine(input);
        System.out.println(result);
    }

    private static class TestIOHandler implements IOHandler {
        @Override
        public String readLine() {
            return null;
        }

        @Override
        public void writeLine(String message) {
            System.out.println(message);
        }
    }
}
