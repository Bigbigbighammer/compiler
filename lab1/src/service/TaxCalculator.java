package service;

import model.entity.TaxConfig;
import model.entity.TaxRule;
import model.entity.TaxTable;

/**
 * 税收相关类
 * @author Aaron
 */
public class TaxCalculator {
    private TaxConfig taxConfig;

    public TaxCalculator(TaxConfig taxConfig) {
        this.taxConfig = taxConfig;
    }

    public TaxConfig getTaxConfig() {
        return taxConfig;
    }

    public void setTaxConfig(TaxConfig taxConfig) {
        this.taxConfig = taxConfig;
    }

    public double calculateTax(double salary) {
        TaxChain taxChain = buildTaxChain(taxConfig);
        double salaryBeyondThreshold = salary - taxConfig.getThreshold();
        if (salaryBeyondThreshold < 0) {
            return 0;
        }
        return taxChain.calculate(salaryBeyondThreshold);
    }

    private TaxChain buildTaxChain(TaxConfig taxConfig) {
        TaxChain taxChain = new TaxChain();
        TaxTable taxTable = taxConfig.getTaxTable();
        for (TaxRule taxRule : taxTable.getTaxRulesList()) {
            taxChain.addLastHandler(new BaseTaxHandler(taxRule));
        }
        return taxChain;
    }

}
