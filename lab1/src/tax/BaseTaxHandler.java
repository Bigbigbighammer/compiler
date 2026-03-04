package tax;

public class BaseTaxHandler implements TaxHandler{

    private final TaxRule taxRule;

    public BaseTaxHandler(TaxRule taxRule) {
        this.taxRule = taxRule;
    }

    @Override
    public void calculate(TaxContext taxContext) {
        double salary = taxContext.getSalary();
        double min = taxRule.getMin();
        double max = taxRule.getMax();
        if (salary < min) {
            taxContext.stopChain();
            return;
        }
        double part = Math.min(max, salary) - min;
        double tax = part * taxRule.getRate();
        taxContext.addTax(tax);
    }
}
