package model.loader;

import model.entity.TaxConfig;

/**
 * 税收配置持久化接口
 *
 * 定义了将TaxConfig对象保存到各种存储介质的统一方法。
 * 支持不同的持久化实现（JSON文件、数据库等）。
 *
 * <h2>设计模式</h2>
 * <p>该接口遵循策略模式(Strategy Pattern)，允许在运行时
 * 选择不同的持久化策略，而无需修改客户端代码。</p>
 *
 * <h2>实现类</h2>
 * <ul>
 *   <li>JsonFileTaxConfigPersister - JSON文件持久化</li>
 *   <li>可扩展：数据库持久化、XML持久化等</li>
 * </ul>
 *
 * <h2>使用示例</h2>
 * <pre>
 * TaxConfig config = new TaxConfig(2000, taxTable);
 * TaxConfigPersister persister = new JsonFileTaxConfigPersister("config.json");
 * persister.save(config);
 * </pre>
 *
 * @author Aaron
 * @version 1.0.0
 * @see TaxConfig
 * @see JsonFileTaxConfigPersister
 */
public interface TaxConfigPersister {

    /**
     * 将税收配置保存到存储介质
     *
     * <p>该方法应确保：
     * <ul>
     *   <li>配置数据完整性</li>
     *   <li>文件/数据库格式正确</li>
     *   <li>所有必需的字段都被保存</li>
     *   <li>发生异常时进行适当的错误处理</li>
     * </ul>
     * </p>
     *
     * @param config 要保存的TaxConfig对象
     * @throws RuntimeException 当保存失败时抛出异常
     * @throws Exception 其他IO或数据库异常
     * @see TaxConfig
     */
    void save(TaxConfig config) throws Exception;

    /**
     * 获取持久化目标的位置/标识
     *
     * @return 目标位置（如文件路径或数据库连接串）
     */
    String getTarget();
}

