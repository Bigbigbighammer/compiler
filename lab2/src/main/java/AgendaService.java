import ui.ConsoleIOHandler;
import ui.IOHandler;

public class AgendaService {

    public static void main(String[] args) {
        AppContext appContext = new AppContext(new ConsoleIOHandler());
        appContext.init();
        appContext.getExecutor().start();
    }
}
