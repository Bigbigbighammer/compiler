import model.entity.TaxConfig;
import model.entity.TaxRule;
import model.entity.TaxTable;
import model.loader.DefaultTaxConfigLoader;
import service.TaxCalculator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Regression tests for tax calculator
 * These tests ensure that core functionality does not regress
 */
public class RegressionTest {

    @Test
    public void testRegressionCoreFunctionality() {
        // Core functionality regression test
        // These values should never change
        TaxConfig config = createStandardConfig();
        TaxCalculator calculator = new TaxCalculator(config);

        // Test critical business cases
        double[][] testCases = {
            {0.0, 0.0},
            {1600.0, 0.0},
            {2100.0, 25.0},
            {3000.0, 115.0},
            {5000.0, 385.0},
            {10000.0, 1305.0},
            {30000.0, 5725.0}
        };

        for (double[] testCase : testCases) {
            double salary = testCase[0];
            double expectedTax = testCase[1];
            double actualTax = calculator.calculateTax(salary);

            assertEquals(expectedTax, actualTax, 0.001,
                String.format("Salary %.2f should have tax %.2f, got %.2f",
                    salary, expectedTax, actualTax));
        }
    }

    @Test
    public void testRegressionBoundaryConditions() {
        // Boundary condition regression tests
        TaxConfig config = createStandardConfig();
        TaxCalculator calculator = new TaxCalculator(config);

        // Test boundaries between tax brackets
        assertEquals(0.0, calculator.calculateTax(1600.0), 0.001); // At threshold
        assertEquals(0.05, calculator.calculateTax(1601.0), 0.001); // Just above threshold

        // At bracket boundaries
        assertEquals(25.0, calculator.calculateTax(2100.0), 0.001); // At 500 bracket
        assertEquals(175.0, calculator.calculateTax(3600.0), 0.001); // At 2000 bracket
        assertEquals(625.0, calculator.calculateTax(6600.0), 0.001); // At 5000 bracket
        assertEquals(3625.0, calculator.calculateTax(21600.0), 0.001); // At 20000 bracket
    }

    @Test
    public void testRegressionConsistency() {
        // Test that calculations are consistent
        TaxConfig config = createStandardConfig();
        TaxCalculator calculator = new TaxCalculator(config);

        // Multiple calculations should give same result
        for (int i = 0; i < 10; i++) {
            double tax1 = calculator.calculateTax(3000.0);
            double tax2 = calculator.calculateTax(3000.0);
            assertEquals(tax1, tax2, 0.001, "Calculations should be consistent");
        }

        // Test multiple different salaries
        double[] salaries = {0.0, 1600.0, 2100.0, 3000.0, 5000.0, 10000.0};
        double[] results1 = new double[salaries.length];
        double[] results2 = new double[salaries.length];

        for (int i = 0; i < salaries.length; i++) {
            results1[i] = calculator.calculateTax(salaries[i]);
        }

        for (int i = 0; i < salaries.length; i++) {
            results2[i] = calculator.calculateTax(salaries[i]);
        }

        for (int i = 0; i < salaries.length; i++) {
            assertEquals(results1[i], results2[i], 0.001,
                String.format("Salary %.2f should have consistent tax", salaries[i]));
        }
    }

    @Test
    public void testRegressionWithDefaultLoader() {
        // Regression test with default configuration loader
        DefaultTaxConfigLoader loader = new DefaultTaxConfigLoader();
        TaxConfig config = loader.load();
        TaxCalculator calculator = new TaxCalculator(config);

        // Verify default configuration
        assertEquals(1600.0, config.getThreshold(), 0.001);
        assertEquals(5, config.getTaxTable().getTaxRulesList().size());

        // Test key calculations with default config
        assertEquals(0.0, calculator.calculateTax(1600.0), 0.001);
        assertEquals(25.0, calculator.calculateTax(2100.0), 0.001);
        assertEquals(115.0, calculator.calculateTax(3000.0), 0.001);
        assertEquals(385.0, calculator.calculateTax(5000.0), 0.001);
    }

    @Test
    public void testRegressionMonotonicity() {
        // Tax should be monotonic: higher salary = higher (or equal) tax
        TaxConfig config = createStandardConfig();
        TaxCalculator calculator = new TaxCalculator(config);

        double[] salaries = {0.0, 1000.0, 1600.0, 2100.0, 3000.0, 5000.0, 10000.0, 30000.0};
        double prevTax = -1.0;

        for (double salary : salaries) {
            double currentTax = calculator.calculateTax(salary);

            // Tax should be non-negative
            assertTrue(currentTax >= 0.0,
                String.format("Tax for salary %.2f should be non-negative, got %.2f",
                    salary, currentTax));

            // Tax should be monotonic (non-decreasing)
            assertTrue(currentTax >= prevTax,
                String.format("Tax for salary %.2f (%.2f) should be >= tax for previous salary (%.2f)",
                    salary, currentTax, prevTax));

            prevTax = currentTax;
        }
    }

    @Test
    public void testRegressionErrorCases() {
        // Regression test for error/edge cases
        TaxConfig config = createStandardConfig();
        TaxCalculator calculator = new TaxCalculator(config);

        // Negative salaries should return 0
        assertEquals(0.0, calculator.calculateTax(-1000.0), 0.001);
        assertEquals(0.0, calculator.calculateTax(-1.0), 0.001);
        assertEquals(0.0, calculator.calculateTax(-0.001), 0.001);

        // Very large salaries should not crash
        double largeSalary = 1000000.0; // 1 million
        double largeTax = calculator.calculateTax(largeSalary);
        assertTrue(largeTax > 0.0, "Large salary should have positive tax");
        assertTrue(Double.isFinite(largeTax), "Large salary tax should be finite");
    }

    @Test
    public void testRegressionConfigModification() {
        // Test that configuration modifications work correctly
        TaxConfig config = createStandardConfig();
        TaxCalculator calculator = new TaxCalculator(config);

        // Record original values
        double originalThreshold = config.getThreshold();
        double originalTaxFor3000 = calculator.calculateTax(3000.0);

        // Modify threshold
        config.setThreshold(2000.0);
        assertEquals(0.0, calculator.calculateTax(1999.0), 0.001);
        assertEquals(0.05, calculator.calculateTax(2001.0), 0.001);

        // Restore original threshold
        config.setThreshold(originalThreshold);
        assertEquals(originalTaxFor3000, calculator.calculateTax(3000.0), 0.001,
            "Restoring threshold should restore original tax calculation");
    }

    @Test
    public void testRegressionPerformance() {
        // Simple performance regression test (not timing, just ensure no crashes)
        TaxConfig config = createStandardConfig();
        TaxCalculator calculator = new TaxCalculator(config);

        // Calculate many different salaries
        int iterations = 1000;
        double totalTax = 0.0;

        for (int i = 0; i < iterations; i++) {
            double salary = i * 100.0; // From 0 to 99000
            double tax = calculator.calculateTax(salary);
            totalTax += tax;

            // Basic sanity check
            assertTrue(tax >= 0.0, "Tax should be non-negative");
            assertTrue(Double.isFinite(tax), "Tax should be finite");
        }

        // Total tax should be positive (most salaries are above threshold)
        assertTrue(totalTax > 0.0, "Total tax should be positive");
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