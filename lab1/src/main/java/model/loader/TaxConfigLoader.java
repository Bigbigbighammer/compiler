package model.loader;

import model.entity.TaxConfig;

/**
 * 税收配置加载器接口
 *
 * 定义了加载税收配置的标准接口。实现此接口的类可以从不同的数据源
 * （如JSON文件、数据库、配置服务器等）加载税收配置。
 *
 * <h2>实现类</h2>
 * <ul>
 *   <li>{@link JsonFileTaxConfigLoader} - 从JSON文件加载配置</li>
 *   <li>{@link DefaultTaxConfigLoader} - 使用默认配置</li>
 *   <li>{@link TaxConfigLoaderFactory} - 工厂类，用于创建加载器实例</li>
 * </ul>
 *
 * <h2>使用示例</h2>
 * <pre>
 * // 从JSON文件加载配置
 * TaxConfigLoader loader = new JsonFileTaxConfigLoader("settings.json");
 * TaxConfig config = loader.load();
 *
 * // 使用工厂模式加载配置
 * TaxConfigLoader loader = TaxConfigLoaderFactory.getLoader("json");
 * TaxConfig config = loader.load();
 * </pre>
 *
 * <h2>配置格式要求</h2>
 * <p>配置文件应包含以下信息：</p>
 * <ul>
 *   <li>threshold：个人所得税起征点</li>
 *   <li>taxTable：包含多条税率规则的税率表</li>
 * </ul>
 *
 * @author Aaron
 * @version 1.0.0
 * @see JsonFileTaxConfigLoader
 * @see DefaultTaxConfigLoader
 * @see TaxConfigLoaderFactory
 * @see TaxConfig
 */
public interface TaxConfigLoader {

    /**
     * 加载税收配置
     *
     * 从配置源加载税收配置信息，包括起征点和税率表。
     * 实现类应确保返回的TaxConfig对象是有效的和完整的。
     *
     * @return 加载后的TaxConfig对象，包含起征点和税率表
     * @throws Exception 当加载失败时抛出异常
     *
     * @see TaxConfig
     */
    TaxConfig load() throws Exception;
}