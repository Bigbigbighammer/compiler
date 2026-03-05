package tax;

import java.util.ArrayList;
import java.util.List;

public class DefaultTaxConfigLoader implements TaxConfigLoader{
    @Override
    public TaxConfig load() {
        System.out.println(">> [Loader] 使用默认硬编码配置加载...");

        TaxTable table = new TaxTable();
        List<TaxRule> rules = new ArrayList<>();
        // 2006年标准
        rules.add(new TaxRule(1, 0, 500, 0.05));
        rules.add(new TaxRule(2, 500, 2000, 0.10));
        rules.add(new TaxRule(3, 2000, 5000, 0.15));
        rules.add(new TaxRule(4, 5000, 20000, 0.20));
        rules.add(new TaxRule(5, 20000, Double.MAX_VALUE, 0.25));
        table.resetRules(rules);

        return new TaxConfig(1600, table);
    }
}
