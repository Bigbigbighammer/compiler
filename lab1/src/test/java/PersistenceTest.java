import model.entity.TaxConfig;
import model.entity.TaxRule;
import model.entity.TaxTable;
import model.loader.TaxConfigLoaderFactory;
import model.loader.TaxConfigPersister;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 配置持久化功能测试
 *
 * 测试TaxConfig对象的保存和加载功能，验证JSON序列化和反序列化的正确性。
 *
 * @author GitHub Copilot
 * @version 1.0.0
 */
public class PersistenceTest {

    private TaxConfig testConfig;
    private static final String TEST_FILE_PATH = "test-config.json";

    /**
     * 测试前准备：创建测试配置对象
     */
    @BeforeEach
    public void setUp() {
        // Create test tax config
        TaxTable taxTable = new TaxTable();
        taxTable.addTaxRule(new TaxRule(1, 0, 500, 0.05));
        taxTable.addTaxRule(new TaxRule(2, 500, 2000, 0.10));
        taxTable.addTaxRule(new TaxRule(3, 2000, 5000, 0.15));
        taxTable.addTaxRule(new TaxRule(4, 5000, 20000, 0.20));
        taxTable.addTaxRule(new TaxRule(5, 20000, Double.MAX_VALUE, 0.25));

        testConfig = new TaxConfig(2000, taxTable);
    }

    /**
     * 测试配置保存功能
     * 验证TaxConfig对象是否正确保存为JSON文件
     */
    @Test
    public void testSaveConfiguration() throws Exception {
        // Get persister and save config
        TaxConfigPersister persister = TaxConfigLoaderFactory.getPersister("json", TEST_FILE_PATH);
        persister.save(testConfig);

        // Verify file exists
        assertTrue(Files.exists(Paths.get(TEST_FILE_PATH)), "Configuration file should be created");

        // Read file content for verification
        String fileContent = new String(Files.readAllBytes(Paths.get(TEST_FILE_PATH)));
        assertTrue(fileContent.contains("\"threshold\": 2000"), "File should contain threshold value");
        assertTrue(fileContent.contains("\"taxRulesList\""), "File should contain tax rules list");

        // Clean up
        Files.delete(Paths.get(TEST_FILE_PATH));
    }

    /**
     * 测试使用默认持久化器
     * 验证使用工厂方法获取的默认JSON持久化器
     */
    @Test
    public void testDefaultPersister() throws Exception {
        TaxConfigPersister persister = TaxConfigLoaderFactory.getDefaultPersister();
        assertNotNull(persister, "Default persister should not be null");
        assertEquals("settings.json", persister.getTarget(), "Default persister should use settings.json");
    }

    /**
     * 测试保存到不同路径
     * 验证持久化器是否能够创建子目录并保存文件
     */
    @Test
    public void testSaveToSubdirectory() throws Exception {
        String testPath = "test-output/configs/test-config.json";
        TaxConfigPersister persister = TaxConfigLoaderFactory.getPersister("json", testPath);

        persister.save(testConfig);

        // Verify file exists
        assertTrue(Files.exists(Paths.get(testPath)), "Configuration file should be created in subdirectory");

        // Clean up
        Files.delete(Paths.get(testPath));
        Files.delete(Paths.get("test-output/configs"));
        Files.delete(Paths.get("test-output"));
    }

    /**
     * 测试null配置处理
     * 验证持久化器是否正确处理null配置
     */
    @Test
    public void testNullConfigHandling() {
        TaxConfigPersister persister = TaxConfigLoaderFactory.getPersister("json", TEST_FILE_PATH);

        assertThrows(RuntimeException.class, () -> persister.save(null),
                "Persister should throw exception for null config");
    }

    /**
     * 测试配置完整性
     * 验证保存的配置包含所有必要的信息
     */
    @Test
    public void testConfigurationCompleteness() throws Exception {
        TaxConfigPersister persister = TaxConfigLoaderFactory.getPersister("json", TEST_FILE_PATH);
        persister.save(testConfig);

        String fileContent = new String(Files.readAllBytes(Paths.get(TEST_FILE_PATH)));

        // Verify all tax rules are saved
        assertTrue(fileContent.contains("\"grade\": 1"), "Grade 1 rule should be saved");
        assertTrue(fileContent.contains("\"grade\": 2"), "Grade 2 rule should be saved");
        assertTrue(fileContent.contains("\"grade\": 3"), "Grade 3 rule should be saved");
        assertTrue(fileContent.contains("\"grade\": 4"), "Grade 4 rule should be saved");
        assertTrue(fileContent.contains("\"grade\": 5"), "Grade 5 rule should be saved");

        // Verify tax rates are saved correctly
        assertTrue(fileContent.contains("\"rate\": 0.05"), "5% rate should be saved");
        assertTrue(fileContent.contains("\"rate\": 0.1"), "10% rate should be saved");

        // Clean up
        Files.delete(Paths.get(TEST_FILE_PATH));
    }
}

