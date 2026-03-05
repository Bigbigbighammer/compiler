package tax;

import java.util.ArrayList;
import java.util.List;

/**
 * 税率表
 */
public class TaxTable {

    private final List<TaxRule> taxRulesList = new ArrayList<>();

    public void addTaxRule(TaxRule taxRule) {
        this.taxRulesList.add(taxRule);
    }

    public List<TaxRule> getTaxRulesList() {
        return taxRulesList;
    }

    public void resetRules(List<TaxRule> newRules) {
        this.taxRulesList.clear();
        this.taxRulesList.addAll(newRules);
    }
}
