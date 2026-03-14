import model.entity.TaxConfig;
import model.entity.TaxRule;
import model.entity.TaxTable;
import model.loader.JsonFileTaxConfigLoader;
import model.loader.TaxConfigLoader;

/**
 * 测试JSON配置加载功能
 */
public class JsonLoaderFunctionalTest {
    public static void main(String[] args) throws Exception {
        System.out.println("=== JSON配置加载功能测试 ===\n");

        // 创建加载器
        TaxConfigLoader loader = new JsonFileTaxConfigLoader("settings.json");

        // 加载配置
        TaxConfig config = loader.load();

        // 验证起征点
        System.out.println("✓ 起征点: " + config.getThreshold() + " 元");
        assert config.getThreshold() == 1600.0 : "起征点应该是1600.0";

        // 验证税率表
        TaxTable taxTable = config.getTaxTable();
        assert taxTable != null : "税率表不应该为空";

        java.util.List<TaxRule> rules = taxTable.getTaxRulesList();
        System.out.println("✓ 税率规则数量: " + rules.size());
        assert rules.size() == 5 : "应该有5条税率规则";

        // 验证第一条规则
        TaxRule firstRule = rules.get(0);
        System.out.println("✓ 第一条规则: 等级=" + firstRule.getGrade() +
                         ", 最小=" + firstRule.getMin() +
                         ", 最大=" + firstRule.getMax() +
                         ", 税率=" + firstRule.getRate());
        assert firstRule.getGrade() == 1 : "第一条规则等级应该是1";
        assert firstRule.getMin() == 0.0 : "第一条规则最小值应该是0";
        assert firstRule.getMax() == 500.0 : "第一条规则最大值应该是500";
        assert firstRule.getRate() == 0.05 : "第一条规则税率应该是0.05";

        // 验证最后一条规则
        TaxRule lastRule = rules.get(4);
        System.out.println("✓ 最后一条规则: 等级=" + lastRule.getGrade() +
                         ", 最小=" + lastRule.getMin() +
                         ", 最大=" + lastRule.getMax() +
                         ", 税率=" + lastRule.getRate());
        assert lastRule.getGrade() == 5 : "最后一条规则等级应该是5";
        assert lastRule.getRate() == 0.25 : "最后一条规则税率应该是0.25";

        System.out.println("\n✓ 所有测试通过！JSON配置加载功能正常。");
    }
}

