package model.loader;

import model.entity.TaxConfig;
import model.entity.TaxRule;
import model.entity.TaxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认税收配置加载器
 *
 * <p>使用硬编码的默认配置，提供内置的2006年标准的个人所得税税率表。
 * 当无法从其他配置源（如JSON文件）加载配置时，可以作为备选方案使用。</p>
 *
 * <h2>默认配置内容</h2>
 * <ul>
 *   <li><b>起征点</b>：1600元（2006年标准）</li>
 *   <li><b>税率等级</b>：5级</li>
 *   <li><b>税率规则</b>：按收入金额分为5个等级，分别适用不同税率</li>
 * </ul>
 *
 * <h2>税率规则详情</h2>
 * <pre>
 * 级数 | 收入范围 | 税率
 * -----|--------|-----
 *  1   | 0-500  | 5%
 *  2   | 500-2000 | 10%
 *  3   | 2000-5000 | 15%
 *  4   | 5000-20000 | 20%
 *  5   | 20000+ | 25%
 * </pre>
 *
 * <h2>使用场景</h2>
 * <ul>
 *   <li>JSON配置文件加载失败时</li>
 *   <li>需要快速测试程序时</li>
 *   <li>需要已知的标准配置时</li>
 * </ul>
 *
 * <h2>使用示例</h2>
 * <pre>
 * TaxConfigLoader loader = new DefaultTaxConfigLoader();
 * TaxConfig config = loader.load();
 * double threshold = config.getThreshold();  // 1600.0
 * </pre>
 *
 * @author Aaron
 * @version 1.0.0
 * @see TaxConfigLoader
 * @see JsonFileTaxConfigLoader
 * @see TaxConfigLoaderFactory
 */
public class DefaultTaxConfigLoader implements TaxConfigLoader{

    /**
     * 加载默认的税收配置
     *
     * <p>返回一个包含2006年标准税率表的默认TaxConfig对象。
     * 起征点固定为1600元，包含5个级次的税率规则。</p>
     *
     * @return 包含默认配置的TaxConfig对象，永远不为null
     * @throws RuntimeException 当配置初始化失败时抛出
     */
    @Override
    public TaxConfig load() {
        System.out.println(">> [Loader] 使用默认硬编码配置加载...");

        try {
            TaxTable table = new TaxTable();
            List<TaxRule> rules = new ArrayList<>();
            // 2006年标准
            rules.add(new TaxRule(1, 0, 500, 0.05));
            rules.add(new TaxRule(2, 500, 2000, 0.10));
            rules.add(new TaxRule(3, 2000, 5000, 0.15));
            rules.add(new TaxRule(4, 5000, 20000, 0.20));
            rules.add(new TaxRule(5, 20000, Double.MAX_VALUE, 0.25));
            table.resetRules(rules);

            TaxConfig config = new TaxConfig(1600, table);

            if (config == null) {
                throw new RuntimeException("Failed to create default TaxConfig");
            }

            System.out.println("✓ 默认配置加载成功");
            return config;
        } catch (RuntimeException e) {
            System.err.println("❌ 默认配置加载失败：" + e.getMessage());
            throw new RuntimeException("Failed to load default tax configuration", e);
        }
    }
}
