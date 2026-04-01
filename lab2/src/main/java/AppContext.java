import command.enums.CommandType;
import command.executor.CommandExecutor;
import command.impl.AddMeetingCommand;
import command.impl.BatchCommand;
import command.impl.ClearCommand;
import command.impl.DeleteCommand;
import command.impl.QueryMeetingCommand;
import command.impl.QuitCommand;
import command.impl.RegisterCommand;
import command.parse.SimpleCommandParser;
import command.registry.CommandRegistry;
import mapper.MeetingMapper;
import mapper.UserMapper;
import mapper.impl.DefaultMeetingMapper;
import mapper.impl.DefaultUserMapper;
import service.MeetingService;
import service.UserService;
import ui.IOHandler;

public class AppContext {

    private final CommandRegistry registry = new CommandRegistry();
    private UserService userService;
    private MeetingService meetingService;

    public void init() {
        UserMapper userMapper = new DefaultUserMapper();
        MeetingMapper meetingMapper = new DefaultMeetingMapper();

        userService = new UserService(userMapper);
        meetingService = new MeetingService(meetingMapper, userService);

        registry.registerCommand(CommandType.REGISTER,
                new RegisterCommand(userService));

        registry.registerCommand(CommandType.ADD,
                new AddMeetingCommand(userService, meetingService));

        registry.registerCommand(CommandType.QUERY,
                new QueryMeetingCommand(userService, meetingService));

        registry.registerCommand(CommandType.DELETE,
                new DeleteCommand(userService, meetingService));

        registry.registerCommand(CommandType.CLEAR,
                new ClearCommand(userService, meetingService));

        registry.registerCommand(CommandType.QUIT,
                new QuitCommand());
    }

    public CommandExecutor createExecutor(IOHandler ioHandler) {
        CommandExecutor executor = new CommandExecutor(registry, new SimpleCommandParser(), ioHandler);
        registry.registerCommand(CommandType.BATCH, new BatchCommand(executor));
        return executor;
    }

    public CommandRegistry getRegistry() {
        return registry;
    }

    public UserService getUserService() {
        return userService;
    }

    public MeetingService getMeetingService() {
        return meetingService;
    }
}