package service;

/**
 * 税收计算上下文
 *
 * 在责任链模式中用于传递和维护计算过程中的状态信息。
 * 包含待处理的剩余收入、已计算的税费和处理链的控制状态。
 *
 * <h2>主要职责</h2>
 * <ul>
 *   <li>存储待处理的收入金额</li>
 *   <li>累积计算得到的总税费</li>
 *   <li>控制处理链的继续或停止</li>
 * </ul>
 *
 * <h2>状态变化过程示例</h2>
 * <pre>
 * 初始状态: salary=1400, tax=0, stop=false
 *    (处理器1: 5%,处理0-500区间)
 * 处理器1输出: tax=25, salary=900
 *    (处理器2: 10%,处理500-2000区间)
 * 处理器2输出: tax=115, salary=0
 *    (处理器3: 15%,收入不足)
 * 处理器3: 标记stop=true
 *    (链停止)
 * 最终结果: tax=115
 * </pre>
 *
 * @author GitHub Copilot
 * @version 1.0.0
 * @see TaxHandler
 * @see TaxChain
 * @see BaseTaxHandler
 */
public class TaxContext {

    /** 已计算的税费总额（元） */
    private double tax = 0;

    /** 待处理的剩余收入（元） */
    private double salary = 0;

    /** 处理链是否应该停止 */
    private boolean stop = false;

    /**
     * 构造函数
     *
     * @param salary 待处理的收入金额（元）
     */
    public TaxContext(double salary) {
        this.salary = salary;
    }

    /**
     * 设置待处理的收入
     *
     * <p>更新上下文中的待处理收入金额。该方法主要在处理器中使用
     * 来更新剩余待处理的收入。</p>
     *
     * @param money 收入金额（元）
     */
    public void setSalary(double money) {
        this.salary = money;
    }

    /**
     * 获取当前待处理的收入
     *
     * @return 待处理收入金额（元）
     */
    public double getSalary() {
        return this.salary;
    }

    /**
     * 标记处理链应该停止
     *
     * <p>当某个处理器判断剩余收入已经全部处理或已低于其范围时，
     * 调用此方法通知处理链停止继续执行。</p>
     */
    public void stopChain() {
        this.stop = true;
    }

    /**
     * 检查处理链是否应该停止
     *
     * @return 如果处理链应该停止返回true，否则返回false
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * 累加已计算的税费
     *
     * <p>处理器计算出该处理范围内的税费后，通过此方法累加到总税费中。</p>
     *
     * @param money 该步骤计算出的税费（元）
     */
    public void addTax(double money) {
        this.tax += money;
    }

    /**
     * 获取最终计算的总税费
     *
     * @return 累积计算的总税费（元）
     */
    public double getFinalTax() {
        return this.tax;
    }
}
