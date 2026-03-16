import model.entity.TaxConfig;
import model.entity.TaxRule;
import model.entity.TaxTable;
import model.loader.DefaultTaxConfigLoader;
import org.junit.jupiter.api.Test;
import service.TaxCalculator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic tax calculation tests
 */
public class BasicTaxTest {

    @Test
    public void testTaxCalculationBelowThreshold() {
        // Salary below threshold should have zero tax
        TaxConfig config = createStandardConfig();
        TaxCalculator calculator = new TaxCalculator(config);

        assertEquals(0.0, calculator.calculateTax(1600.0), 0.001);
        assertEquals(0.0, calculator.calculateTax(1000.0), 0.001);
        assertEquals(0.0, calculator.calculateTax(0.0), 0.001);
    }

    @Test
    public void testTaxCalculationBasicCases() {
        // Basic tax calculation test cases
        TaxConfig config = createStandardConfig();
        TaxCalculator calculator = new TaxCalculator(config);

        // Salary 2100: taxable income = 2100-1600 = 500, tax = 500*5% = 25
        assertEquals(25.0, calculator.calculateTax(2100.0), 0.001);

        // Salary 3000: taxable income = 1400
        // First 500: 500*5% = 25
        // Next 900: 900*10% = 90
        // Total: 115
        assertEquals(115.0, calculator.calculateTax(3000.0), 0.001);

        // Salary 5000: taxable income = 3400
        // 500*5% = 25
        // 1500*10% = 150
        // 1400*15% = 210
        // Total: 385
        assertEquals(385.0, calculator.calculateTax(5000.0), 0.001);
    }

    @Test
    public void testTaxCalculationHighSalary() {
        // Test high salary calculations
        TaxConfig config = createStandardConfig();
        TaxCalculator calculator = new TaxCalculator(config);

        // Salary 10000: taxable income = 8400
        // 25 + 150 + 450 + (8400-5000)*20% = 25+150+450+680 = 1305
        assertEquals(1305.0, calculator.calculateTax(10000.0), 0.001);

        // Salary 30000: taxable income = 28400
        // 25 + 150 + 450 + 3000 + (28400-20000)*25% = 25+150+450+3000+2100 = 5725
        assertEquals(5725.0, calculator.calculateTax(30000.0), 0.001);
    }

    @Test
    public void testDefaultConfigLoader() {
        // Test default configuration loader
        DefaultTaxConfigLoader loader = new DefaultTaxConfigLoader();
        TaxConfig config = loader.load();

        assertNotNull(config);
        assertEquals(1600.0, config.getThreshold(), 0.001);
        assertNotNull(config.getTaxTable());
        assertEquals(5, config.getTaxTable().getTaxRulesList().size());
    }

    @Test
    public void testNegativeSalary() {
        // Negative salary should return zero tax
        TaxConfig config = createStandardConfig();
        TaxCalculator calculator = new TaxCalculator(config);

        assertEquals(0.0, calculator.calculateTax(-1000.0), 0.001);
        assertEquals(0.0, calculator.calculateTax(-1.0), 0.001);
    }

    @Test
    public void testZeroSalary() {
        // Zero salary should return zero tax
        TaxConfig config = createStandardConfig();
        TaxCalculator calculator = new TaxCalculator(config);

        assertEquals(0.0, calculator.calculateTax(0.0), 0.001);
    }

    @Test
    public void testTaxConfigModification() {
        // Test that tax config can be modified
        TaxConfig config = createStandardConfig();
        TaxCalculator calculator = new TaxCalculator(config);

        // Change threshold
        config.setThreshold(2000.0);
        assertEquals(0.0, calculator.calculateTax(1999.0), 0.001);
        assertEquals(0.05, calculator.calculateTax(2001.0), 0.001); // (2001-2000)*5% = 0.05
    }

    @Test
    public void testTaxCalculatorReuse() {
        // Test that calculator can be reused
        TaxConfig config = createStandardConfig();
        TaxCalculator calculator = new TaxCalculator(config);

        double tax1 = calculator.calculateTax(3000.0);
        double tax2 = calculator.calculateTax(3000.0);
        double tax3 = calculator.calculateTax(5000.0);

        assertEquals(tax1, tax2, 0.001); // Same input should give same output
        assertEquals(115.0, tax1, 0.001); // Should be 115
        assertEquals(385.0, tax3, 0.001); // Should be 385
    }

    private TaxConfig createStandardConfig() {
        // Create standard tax configuration (2006 standard)
        TaxTable table = new TaxTable();
        List<TaxRule> rules = new ArrayList<>();
        rules.add(new TaxRule(1, 0, 500, 0.05));
        rules.add(new TaxRule(2, 500, 2000, 0.10));
        rules.add(new TaxRule(3, 2000, 5000, 0.15));
        rules.add(new TaxRule(4, 5000, 20000, 0.20));
        rules.add(new TaxRule(5, 20000, Double.MAX_VALUE, 0.25));
        table.resetRules(rules);

        return new TaxConfig(1600, table);
    }
}