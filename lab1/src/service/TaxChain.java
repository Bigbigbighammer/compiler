package service;

import java.util.ArrayList;
import java.util.List;

/**
 * 税收处理链
 *
 * 实现了责任链（Chain of Responsibility）设计模式。将多个税率处理器
 * 链接起来，按顺序处理超出起征点的收入，计算累进税率下的个人所得税。
 *
 * <h2>工作流程</h2>
 * <ol>
 *   <li>创建税收处理链，按顺序添加税率处理器</li>
 *   <li>调用calculate方法传入超出起征点的收入</li>
 *   <li>每个处理器依次对应的收入区间进行税率计算</li>
 *   <li>当某个处理器标记停止或全部处理器处理完毕后，返回总税费</li>
 * </ol>
 *
 * <h2>示例</h2>
 * <pre>
 * TaxChain chain = new TaxChain();
 * chain.addLastHandler(new BaseTaxHandler(rule1));  // 5%
 * chain.addLastHandler(new BaseTaxHandler(rule2));  // 10%
 * chain.addLastHandler(new BaseTaxHandler(rule3));  // 15%
 *
 * double tax = chain.calculate(1400);  // 超出起征点的收入
 * // 计算结果：500 × 5% + 900 × 10% = 115元
 * </pre>
 *
 * @author GitHub Copilot
 * @version 1.0.0
 * @see TaxHandler
 * @see TaxContext
 * @see BaseTaxHandler
 */
public class TaxChain {

    /** 税率处理器列表 */
    private final List<TaxHandler> taxHandlers = new ArrayList<>();

    /**
     * 添加税率处理器到链的末尾
     *
     * <p>处理器的添加顺序很重要，应该按照收入等级从低到高的顺序添加。</p>
     *
     * @param taxHandler 要添加的税率处理器
     * @see TaxHandler
     * @see BaseTaxHandler
     */
    public void addLastHandler(TaxHandler taxHandler) {
        this.taxHandlers.add(taxHandler);
    }

    /**
     * 计算税费
     *
     * <p>通过责任链模式，按顺序调用各处理器计算税费。
     * 每个处理器处理相应收入区间的税率计算。</p>
     *
     * <p>计算流程：
     * <ol>
     *   <li>创建税收上下文，初始化待处理收入</li>
     *   <li>遍历所有税率处理器</li>
     *   <li>每个处理器计算自己负责的收入区间的税费</li>
     *   <li>当处理器标记停止或全部处理完毕，返回最终税费</li>
     * </ol>
     * </p>
     *
     * @param salary 超出起征点的收入（元）
     * @return 计算后的总税费（元）
     *
     * @see TaxContext
     * @see TaxHandler
     */
    public double calculate(double salary) {
        TaxContext taxContext = new TaxContext(salary);
        for (TaxHandler taxHandler : taxHandlers) {
            taxHandler.calculate(taxContext);
            if (taxContext.shouldStop()) {
                break;
            }
        }
        return taxContext.getFinalTax();
    }

}
