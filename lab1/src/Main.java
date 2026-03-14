import view.TaxConsoleMenu;

/**
 * 个人所得税计算系统主入口
 *
 * 该类是应用程序的主入口类，负责启动个人所得税计算器的控制台交互界面。
 *
 * <h2>程序流程</h2>
 * <ol>
 *   <li>创建TaxConsoleMenu实例</li>
 *   <li>调用start()方法启动交互菜单</li>
 *   <li>用户可以进行税费计算、设置参数等操作</li>
 * </ol>
 *
 * <h2>使用方式</h2>
 * <pre>
 * 直接运行此类：
 * java -cp "lib/gson-2.10.1.jar;bin;src/main/resources" Main
 * </pre>
 *
 * @author GitHub Copilot
 * @version 1.0.0
 * @see TaxConsoleMenu
 */
public class Main {

    /**
     * 程序主方法
     *
     * @param args 命令行参数（当前无需传递）
     */
    public static void main(String[] args) {
        new TaxConsoleMenu().start();
    }
}
