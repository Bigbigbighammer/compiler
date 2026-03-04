import tax.TaxCalculator;
import tax.TaxConfig;
import tax.TaxRule;
import tax.TaxTable;

public class Main {
    public static void main(String[] args) {
        double threshold = 1600;
        TaxRule taxRule1 = new TaxRule(1, 0, 500, 0.05);
        TaxRule taxRule2 = new TaxRule(2, 500, 2000, 0.10);
        TaxRule taxRule3 = new TaxRule(3, 2000, 5000, 0.15);
        TaxRule taxRule4 = new TaxRule(4, 5000, 20000, 0.20);
        TaxRule taxRule5 = new TaxRule(5, 20000, Double.MAX_VALUE, 0.25);
        TaxTable taxTable = new TaxTable();
        taxTable.addTaxRule(taxRule1);
        taxTable.addTaxRule(taxRule2);
        taxTable.addTaxRule(taxRule3);
        taxTable.addTaxRule(taxRule4);
        taxTable.addTaxRule(taxRule5);
        TaxConfig taxConfig = new TaxConfig(threshold, taxTable);
        double salary = 4300;
        TaxCalculator taxCalculator = new TaxCalculator();
        double tax = taxCalculator.calculateTax(salary, taxConfig);
        System.out.println(tax);
    }
}
