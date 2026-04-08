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

/**
 * 应用程序上下文。
 * <p>
 * 负责初始化和管理应用程序的各种组件，包括服务层、命令注册等。
 * 支持为不同的 IOHandler 创建独立的 CommandExecutor。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class AppContext {

    /** 命令注册表 */
    private final CommandRegistry registry = new CommandRegistry();

    /** 用户服务 */
    private UserService userService;

    /** 会议服务 */
    private MeetingService meetingService;

    /**
     * 初始化应用程序上下文。
     * <p>
     * 创建数据访问层、服务层，并注册所有命令。
     * </p>
     */
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

    /**
     * 创建命令执行器。
     * <p>
     * 每次调用创建一个新的执行器，绑定到指定的 IOHandler。
     * </p>
     *
     * @param ioHandler 输入输出处理器
     * @return 命令执行器
     */
    public CommandExecutor createExecutor(IOHandler ioHandler) {
        CommandExecutor executor = new CommandExecutor(registry, new SimpleCommandParser(), ioHandler);
        registry.registerCommand(CommandType.BATCH, new BatchCommand(executor));
        return executor;
    }

    /**
     * 获取命令注册表。
     *
     * @return 命令注册表
     */
    public CommandRegistry getRegistry() {
        return registry;
    }

    /**
     * 获取用户服务。
     *
     * @return 用户服务
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * 获取会议服务。
     *
     * @return 会议服务
     */
    public MeetingService getMeetingService() {
        return meetingService;
    }
}
