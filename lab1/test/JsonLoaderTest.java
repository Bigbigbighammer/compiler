import model.entity.TaxConfig;
import model.entity.TaxRule;
import model.loader.JsonFileTaxConfigLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for JSON configuration loader
 */
public class JsonLoaderTest {

    @Test
    public void testJsonLoaderLoadsConfig() throws Exception {
        // Test loading configuration from JSON file
        JsonFileTaxConfigLoader loader = new JsonFileTaxConfigLoader("settings.json");
        TaxConfig config = loader.load();

        // Verify config is not null
        assertNotNull(config, "Config should not be null");
        assertNotNull(config.getTaxTable(), "Tax table should not be null");
        assertNotNull(config.getTaxTable().getTaxRulesList(), "Tax rules list should not be null");

        // Verify threshold
        assertEquals(1600.0, config.getThreshold(), 0.001, "Threshold should be 1600");

        // Verify tax rules
        List<TaxRule> rules = config.getTaxTable().getTaxRulesList();
        assertEquals(5, rules.size(), "Should have 5 tax rules");

        // Verify first rule
        TaxRule rule1 = rules.get(0);
        assertEquals(1, rule1.getGrade(), "First rule grade should be 1");
        assertEquals(0.0, rule1.getMin(), 0.001, "First rule min should be 0");
        assertEquals(500.0, rule1.getMax(), 0.001, "First rule max should be 500");
        assertEquals(0.05, rule1.getRate(), 0.001, "First rule rate should be 0.05");

        // Verify last rule
        TaxRule rule5 = rules.get(4);
        assertEquals(5, rule5.getGrade(), "Last rule grade should be 5");
        assertEquals(20000.0, rule5.getMin(), 0.001, "Last rule min should be 20000");
        assertEquals(Double.MAX_VALUE, rule5.getMax(), 0.001, "Last rule max should be Double.MAX_VALUE");
        assertEquals(0.25, rule5.getRate(), 0.001, "Last rule rate should be 0.25");
    }

    @Test
    public void testJsonLoaderInvalidFile() {
        // Test with non-existent file
        JsonFileTaxConfigLoader loader = new JsonFileTaxConfigLoader("nonexistent.json");

        Exception exception = assertThrows(Exception.class, loader::load,
            "Should throw exception for non-existent file");

        assertTrue(exception.getMessage().contains("Configuration file not found"),
            "Exception message should indicate file not found");
    }

    @Test
    public void testJsonLoaderConsistency() throws Exception {
        // Test that loader returns consistent results
        JsonFileTaxConfigLoader loader = new JsonFileTaxConfigLoader("settings.json");

        TaxConfig config1 = loader.load();
        TaxConfig config2 = loader.load();

        // Configs should have same threshold
        assertEquals(config1.getThreshold(), config2.getThreshold(), 0.001,
            "Threshold should be consistent");

        // Configs should have same number of rules
        assertEquals(config1.getTaxTable().getTaxRulesList().size(),
            config2.getTaxTable().getTaxRulesList().size(),
            "Number of rules should be consistent");
    }

    @Test
    public void testJsonLoaderUsability() throws Exception {
        // Test that loaded config can be used for tax calculation
        JsonFileTaxConfigLoader loader = new JsonFileTaxConfigLoader("settings.json");
        TaxConfig config = loader.load();

        // Verify config can be used (basic validation)
        assertTrue(config.getThreshold() >= 0, "Threshold should be non-negative");

        for (TaxRule rule : config.getTaxTable().getTaxRulesList()) {
            assertTrue(rule.getMin() >= 0, "Rule min should be non-negative");
            assertTrue(rule.getMax() > rule.getMin(), "Rule max should be greater than min");
            assertTrue(rule.getRate() >= 0 && rule.getRate() <= 1,
                "Rule rate should be between 0 and 1");
        }
    }

    @Test
    public void testJsonLoaderWithTaxCalculator() throws Exception {
        // Test integration with TaxCalculator
        JsonFileTaxConfigLoader loader = new JsonFileTaxConfigLoader("settings.json");
        TaxConfig config = loader.load();

        // Create calculator with JSON config
        service.TaxCalculator calculator = new service.TaxCalculator(config);

        // Test basic calculations
        assertEquals(0.0, calculator.calculateTax(1600.0), 0.001,
            "Salary at threshold should have zero tax");
        assertEquals(25.0, calculator.calculateTax(2100.0), 0.001,
            "Salary 2100 should have tax 25");
        assertEquals(115.0, calculator.calculateTax(3000.0), 0.001,
            "Salary 3000 should have tax 115");
    }
}