package service;

import model.entity.TaxRule;

/**
 * 基础税率处理器
 *
 * 实现TaxHandler接口，负责处理单个税率规则对应的收入区间的税费计算。
 * 该处理器是责任链中的一个环节，处理特定等级的税率。
 *
 * <h2>功能描述</h2>
 * <p>根据配置的税率规则，计算待处理收入中属于该规则收入区间的部分，
 * 按照对应税率计算税费，并更新上下文信息。</p>
 *
 * <h2>计算逻辑示例</h2>
 * <pre>
 * 假设当前处理器规则为：min=500, max=2000, rate=10%
 * 当前待处理收入为：900元
 *
 * 1. 检查：900 >= 500 (min) 成立,继续处理
 * 2. 计算范围内的收入：min(2000, 900) - 500 = 400元
 * 3. 计算税费：400 × 10% = 40元
 * 4. 更新上下文：已收集税费 += 40，待处理收入 = 0
 * </pre>
 *
 * <h2>使用示例</h2>
 * <pre>
 * TaxRule rule = new TaxRule(2, 500, 2000, 0.10);
 * TaxHandler handler = new BaseTaxHandler(rule);
 *
 * TaxContext context = new TaxContext(900);
 * handler.calculate(context);
 * // 结果: 税费40元，待处理收入0元
 * </pre>
 *
 * @author Aaron
 * @version 1.0.0
 * @see TaxHandler
 * @see TaxRule
 * @see TaxContext
 * @see TaxChain
 */
public class BaseTaxHandler implements TaxHandler {

    /** 该处理器对应的税率规则 */
    private final TaxRule taxRule;

    /**
     * 构造函数
     *
     * @param taxRule 该处理器所处理的税率规则对象
     */
    public BaseTaxHandler(TaxRule taxRule) {
        this.taxRule = taxRule;
    }

    /**
     * 计算该税率规则范围内的税费
     *
     * <p>处理过程：</p>
     * <ol>
     *   <li>获取当前待处理收入</li>
     *   <li>如果待处理收入小于该规则的最小值，标记停止</li>
     *   <li>否则，计算该规则范围内应征税的收入部分</li>
     *   <li>按税率计算并累加税费</li>
     * </ol>
     *
     * @param taxContext 税收计算上下文
     *
     * @see TaxRule
     * @see TaxContext
     */
    @Override
    public void calculate(TaxContext taxContext) {
        double salary = taxContext.getSalary();
        double min = taxRule.getMin();
        double max = taxRule.getMax();

        // If remaining salary is below this rule's minimum, stop the chain
        if (salary < min) {
            taxContext.stopChain();
            return;
        }

        // Calculate the part of salary that falls within this rule's range
        double part = Math.min(max, salary) - min;
        double tax = part * taxRule.getRate();
        taxContext.addTax(tax);
    }
}
