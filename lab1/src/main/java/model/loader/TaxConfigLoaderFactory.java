package model.loader;

/**
 * 税收配置加载器工厂
 *
 * 使用工厂模式创建适当的税收配置加载器实例。
 * 根据不同的配置源类型，返回对应的加载器实现。
 *
 * <h2>设计模式</h2>
 * <p>该类实现了工厂模式(Factory Pattern)，通过静态工厂方法
 * 创建不同的TaxConfigLoader实现。这样做的好处是：</p>
 * <ul>
 *   <li>隐藏对象创建的细节</li>
 *   <li>客户端代码与具体实现类解耦</li>
 *   <li>便于添加新的加载器类型</li>
 *   <li>统一的获取配置加载器的方式</li>
 * </ul>
 *
 * <h2>支持的加载器类型</h2>
 * <ul>
 *   <li><b>json</b> - JsonFileTaxConfigLoader，从JSON文件加载配置</li>
 *   <li><b>default</b> - DefaultTaxConfigLoader，使用默认硬编码配置</li>
 * </ul>
 *
 * <h2>使用示例</h2>
 * <pre>
 * // 从JSON文件加载配置
 * TaxConfigLoader loader = TaxConfigLoaderFactory.getLoader("json");
 * TaxConfig config = loader.load();
 *
 * // 使用默认配置
 * TaxConfigLoader defaultLoader = TaxConfigLoaderFactory.getLoader("default");
 * TaxConfig defaultConfig = defaultLoader.load();
 *
 * // 如果type为null或未知，使用默认配置
 * TaxConfigLoader anyLoader = TaxConfigLoaderFactory.getLoader("unknown");
 * </pre>
 *
 * <h2>扩展性</h2>
 * <p>如果需要添加新的加载器类型，只需：</p>
 * <ol>
 *   <li>实现TaxConfigLoader接口的新类</li>
 *   <li>在getLoader方法中添加新的case分支</li>
 *   <li>无需修改客户端代码</li>
 * </ol>
 *
 * @author Aaron
 * @version 1.0.0
 * @see TaxConfigLoader
 * @see JsonFileTaxConfigLoader
 * @see DefaultTaxConfigLoader
 */
public class TaxConfigLoaderFactory {

    /**
     * 根据类型获取相应的税收配置加载器
     *
     * <p>使用工厂方法模式创建加载器实例。根据传入的type参数，
     * 返回对应的TaxConfigLoader实现。</p>
     *
     * 支持的加载器类型：
     * <table border="1">
     *   <caption>支持的加载器类型</caption>
     *   <tr><th>type参数</th><th>返回的加载器</th><th>说明</th></tr>
     *   <tr><td>"json"</td><td>JsonFileTaxConfigLoader</td><td>从JSON文件加载</td></tr>
     *   <tr><td>"default"</td><td>DefaultTaxConfigLoader</td><td>使用默认配置</td></tr>
     *   <tr><td>其他值</td><td>DefaultTaxConfigLoader</td><td>默认返回默认加载器</td></tr>
     * </table>
     *
     * <p>type参数不区分大小写（已转换为小写处理）。</p>
     *
     * @param type 加载器类型："json"或"default"
     * @return 返回相应的TaxConfigLoader实现实例
     * @throws NullPointerException 当type为null时（会被toLowerCase()调用抛出）
     *
     * @see JsonFileTaxConfigLoader
     * @see DefaultTaxConfigLoader
     */
    public static TaxConfigLoader getLoader(String type) {
        switch (type.toLowerCase()) {
            case "json":
                return new JsonFileTaxConfigLoader("settings.json");
//            case "redis":
//                return new RedisTaxConfigLoader("localhost", 6379, "tax_config");
            case "default":
            default:
                return new DefaultTaxConfigLoader();
        }
    }

    /**
     * 根据类型获取相应的税收配置持久化器
     *
     * <p>使用工厂方法模式创建持久化器实例。根据传入的type参数，
     * 返回对应的TaxConfigPersister实现。</p>
     *
     * 支持的持久化器类型：
     * <table border="1">
     *   <caption>支持的持久化器类型</caption>
     *   <tr><th>type参数</th><th>返回的持久化器</th><th>说明</th></tr>
     *   <tr><td>"json"</td><td>JsonFileTaxConfigPersister</td><td>保存到JSON文件</td></tr>
     *   <tr><td>其他值</td><td>JsonFileTaxConfigPersister</td><td>默认使用JSON持久化</td></tr>
     * </table>
     *
     * <p>type参数不区分大小写（已转换为小写处理）。</p>
     *
     * @param type 持久化器类型："json"等
     * @param filePath 保存文件路径
     * @return 返回相应的TaxConfigPersister实现实例
     * @throws NullPointerException 当type或filePath为null时
     *
     * @see JsonFileTaxConfigPersister
     * @see TaxConfigPersister
     */
    public static TaxConfigPersister getPersister(String type, String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        switch (type.toLowerCase()) {
            case "json":
            default:
                return new JsonFileTaxConfigPersister(filePath);
        }
    }

    /**
     * 获取默认的JSON文件持久化器
     *
     * <p>使用默认的settings.json文件路径创建持久化器。</p>
     *
     * @return JsonFileTaxConfigPersister实例
     * @see JsonFileTaxConfigPersister
     */
    public static TaxConfigPersister getDefaultPersister() {
        return new JsonFileTaxConfigPersister("settings.json");
    }
}