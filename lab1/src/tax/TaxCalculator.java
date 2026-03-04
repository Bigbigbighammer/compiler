package tax;

/**
 * 税收相关类
 * @author Aaron
 */
public class TaxCalculator {

    public double calculateTax(double salary, TaxConfig taxConfig) {
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
