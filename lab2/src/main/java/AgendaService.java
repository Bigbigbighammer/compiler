import ui.ConsoleIOHandler;
import ui.IOHandler;

public class AgendaService {

    public static void main(String[] args) {
        AppContext appContext = new AppContext();
        appContext.init();
        IOHandler ioHandler = new ConsoleIOHandler();
        appContext.createExecutor(ioHandler).start();
    }
}
