import ui.ConsoleIOHandler;
import ui.IOHandler;

/**
 * 议程服务主程序入口。
 * <p>
 * 启动本地控制台交互模式。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class AgendaService {

    /**
     * 主方法。
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        AppContext appContext = new AppContext();
        appContext.init();
        IOHandler ioHandler = new ConsoleIOHandler();
        appContext.createExecutor(ioHandler).start();
    }
}
