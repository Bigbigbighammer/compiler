package service;

/**
 * 税率处理器接口
 *
 * 定义了税率处理器的标准接口。实现此接口的类负责处理特定收入区间内的税率计算。
 * 通过链式处理多个处理器，实现阶梯型累进税率的计算。
 *
 * <h2>设计模式</h2>
 * <p>采用责任链（Chain of Responsibility）设计模式。每个处理器只负责
 * 计算自己的收入范围内的税费，然后将控制权传递给下一个处理器。</p>
 *
 * <h2>实现类</h2>
 * <ul>
 *   <li>{@link BaseTaxHandler} - 基础税率处理器实现</li>
 * </ul>
 *
 * <h2>使用示例</h2>
 * <pre>
 * TaxRule rule = new TaxRule(1, 0, 500, 0.05);
 * TaxHandler handler = new BaseTaxHandler(rule);
 *
 * TaxContext context = new TaxContext(400);
 * handler.calculate(context);
 * </pre>
 *
 * @author Aaron
 * @version 1.0.0
 * @see BaseTaxHandler
 * @see TaxContext
 * @see TaxChain
 */
public interface TaxHandler {

    /**
     * 计算该处理器负责范围内的税费
     *
     * <p>根据配置的税率规则，计算对应收入区间内的税费，
     * 并更新税收上下文中的税费和剩余收入。</p>
     *
     * <p><b>计算逻辑</b></p>
     * <ol>
     *   <li>检查当前收入是否在该处理器的范围内</li>
     *   <li>如果在范围内，计算该范围内的税费</li>
     *   <li>更新上下文中的剩余收入和总税费</li>
     *   <li>如果收入已全部处理完，标记停止</li>
     * </ol>
     *
     * @param taxContext 税收计算上下文，包含待处理收入和已计算税费
     *
     * @see TaxContext
     */
    void calculate(TaxContext taxContext);

}
