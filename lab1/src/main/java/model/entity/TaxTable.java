package model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 税率表
 *
 * 管理个人所得税的多条税率规则。每条规则定义了特定收入区间对应的税率，
 * 通过链式处理多条规则来计算个人所得税。
 *
 * <h2>主要功能</h2>
 * <ul>
 *   <li>添加单条税率规则（addTaxRule）</li>
 *   <li>获取所有税率规则列表（getTaxRulesList）</li>
 *   <li>重置整个税率表（resetRules）</li>
 * </ul>
 *
 * <h2>标准税率表示例</h2>
 * <pre>
 * 级数 | 收入范围 | 税率
 * ------+--------+-----
 *  1   | 0-500  | 5%
 *  2   | 500-2000 | 10%
 *  3   | 2000-5000 | 15%
 *  4   | 5000-20000 | 20%
 *  5   | 20000+ | 25%
 * </pre>
 *
 * <h2>使用示例</h2>
 * <pre>
 * TaxTable taxTable = new TaxTable();
 * taxTable.addTaxRule(new TaxRule(1, 0, 500, 0.05));
 * taxTable.addTaxRule(new TaxRule(2, 500, 2000, 0.10));
 * List&lt;TaxRule&gt; rules = taxTable.getTaxRulesList();
 * </pre>
 *
 * @author Aaron
 * @version 1.0.0
 * @see TaxRule
 * @see TaxConfig
 */
public class TaxTable {

    /** 税率规则列表 */
    private final List<TaxRule> taxRulesList = new ArrayList<>();

    /**
     * 添加单条税率规则
     *
     * <p>将新的税率规则添加到税率表的末尾。</p>
     *
     * @param taxRule 要添加的税率规则对象
     * @see TaxRule
     */
    public void addTaxRule(TaxRule taxRule) {
        this.taxRulesList.add(taxRule);
    }

    /**
     * 获取所有税率规则
     *
     * <p>返回当前税率表中包含的所有税率规则的列表。
     * 规则按照添加顺序排列。</p>
     *
     * @return 税率规则列表
     * @see TaxRule
     */
    public List<TaxRule> getTaxRulesList() {
        return taxRulesList;
    }

    /**
     * 重置整个税率表
     *
     * <p>清空现有的所有税率规则，并使用提供的新规则列表替换。
     * 这个方法用于完整地更新税率表。</p>
     *
     * @param newRules 新的税率规则列表
     * @see TaxRule
     */
    public void resetRules(List<TaxRule> newRules) {
        this.taxRulesList.clear();
        this.taxRulesList.addAll(newRules);
    }
}
