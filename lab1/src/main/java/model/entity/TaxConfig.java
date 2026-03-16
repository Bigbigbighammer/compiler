package model.entity;

/**
 * 税务配置实体类
 *
 * 用于存储和管理个人所得税的配置参数，包括起征点（免税额）和完整的税率表。
 * 该类是税收计算系统的核心配置对象。
 *
 * <h2>主要属性</h2>
 * <ul>
 *   <li><b>threshold</b>：个人所得税起征点，默认为1600元</li>
 *   <li><b>taxTable</b>：税率表，包含多条税率规则</li>
 * </ul>
 *
 * <h2>使用示例</h2>
 * <pre>
 * TaxTable taxTable = new TaxTable();
 * // ... 添加税率规则
 * TaxConfig config = new TaxConfig(1600, taxTable);
 *
 * double threshold = config.getThreshold();
 * config.setThreshold(2000);
 * </pre>
 *
 * @author Aaron
 * @version 1.0.0
 * @see TaxTable
 * @see TaxRule
 */
public class TaxConfig {

    /** 个人所得税起征点（免税额），单位：元，默认1600 */
    private double threshold = 1600;

    /** 税率表，包含多条税率规则 */
    private TaxTable taxTable;

    /**
     * 构造函数
     *
     * @param threshold 起征点金额（元）
     * @param taxTable 税率表对象
     */
    public TaxConfig(double threshold, TaxTable taxTable) {
        this.threshold = threshold;
        this.taxTable = taxTable;
    }

    /**
     * 获取当前起征点
     *
     * @return 起征点金额（元）
     */
    public double getThreshold() {
        return threshold;
    }

    /**
     * 设置新的起征点
     *
     * @param threshold 新的起征点金额（元）
     */
    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    /**
     * 获取税率表
     *
     * @return TaxTable对象
     * @see TaxTable
     */
    public TaxTable getTaxTable() {
        return taxTable;
    }

    /**
     * 设置新的税率表
     *
     * @param taxTable 新的TaxTable对象
     */
    public void setTaxTable(TaxTable taxTable) {
        this.taxTable = taxTable;
    }
}
