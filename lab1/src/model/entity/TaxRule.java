package model.entity;

/**
 * 税率规则
 *
 * 表示个人所得税中的单条税率规则。每条规则定义了特定收入区间对应的税率。
 * 通过多条规则的组合，可以形成阶梯型的累进税率体系。
 *
 * <h2>主要属性</h2>
 * <ul>
 *   <li><b>grade</b>：税率等级（1到5级）</li>
 *   <li><b>min</b>：收入最小值（元）</li>
 *   <li><b>max</b>：收入最大值（元）</li>
 *   <li><b>rate</b>：对应的税率（0.0到1.0）</li>
 * </ul>
 *
 * <h2>等级说明</h2>
 * 标准税率表包含以下5个等级：
 * <pre>
 * 等级 | 收入范围 | 税率
 * -----|--------|-----
 *  1   | 0-500  | 5%
 *  2   | 500-2000 | 10%
 *  3   | 2000-5000 | 15%
 *  4   | 5000-20000 | 20%
 *  5   | 20000+ | 25%
 * </pre>
 *
 * <h2>使用示例</h2>
 * <pre>
 * TaxRule rule1 = new TaxRule(1, 0, 500, 0.05);
 * TaxRule rule2 = new TaxRule(2, 500, 2000, 0.10);
 *
 * System.out.println(rule1.getGrade());  // 输出: 1
 * System.out.println(rule1.getRate());   // 输出: 0.05
 * System.out.println(rule1.toString());  // 输出格式化的规则信息
 * </pre>
 *
 * @author GitHub Copilot
 * @version 1.0.0
 * @see TaxTable
 * @see TaxConfig
 */
public class TaxRule {

    /** 税率等级（1到5级） */
    private int grade;

    /** 收入最小值，单位：元 */
    private double min;

    /** 收入最大值，单位：元 */
    private double max;

    /** 税率，范围：0.0到1.0 */
    private double rate;

    /**
     * 构造函数
     *
     * @param grade 税率等级（1到5）
     * @param min 收入最小值（元）
     * @param max 收入最大值（元）
     * @param rate 税率（0.0到1.0）
     */
    public TaxRule(int grade, double min, double max, double rate) {
        this.grade = grade;
        this.min = min;
        this.max = max;
        this.rate = rate;
    }

    /**
     * 获取税率等级
     *
     * @return 等级数字（1到5）
     */
    public int getGrade() {
        return grade;
    }

    /**
     * 设置税率等级
     *
     * @param grade 等级数字
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }

    /**
     * 获取收入最小值
     *
     * @return 最小值（元）
     */
    public double getMin() {
        return min;
    }

    /**
     * 设置收入最小值
     *
     * @param min 最小值（元）
     */
    public void setMin(double min) {
        this.min = min;
    }

    /**
     * 获取收入最大值
     *
     * @return 最大值（元）
     */
    public double getMax() {
        return max;
    }

    /**
     * 设置收入最大值
     *
     * @param max 最大值（元）
     */
    public void setMax(double max) {
        this.max = max;
    }

    /**
     * 获取税率
     *
     * @return 税率（0.0到1.0之间的值）
     */
    public double getRate() {
        return rate;
    }

    /**
     * 设置税率
     *
     * @param rate 税率值（0.0到1.0）
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * 返回税率规则的字符串表示
     *
     * <p>格式示例：
     * "级数  1 | 0 元 至 500 元 | 税率 5%"
     * "级数  5 | 超过 20000 元的部分 | 税率 25%"
     * </p>
     *
     * @return 格式化的规则描述字符串
     */
    @Override
    public String toString() {
        String range;
        if (max == Double.MAX_VALUE) {
            range = String.format("超过 %8.0f 元的部分", min);
        } else {
            range = String.format("%8.0f 元 至 %8.0f 元", min, max);
        }
        return String.format("级数 %d | %-20s | 税率 %.0f%%", grade, range, rate * 100);
    }
}
