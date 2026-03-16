import model.entity.TaxConfig;
import model.entity.TaxRule;
import model.entity.TaxTable;
import model.loader.TaxConfigLoader;
import model.loader.TaxConfigLoaderFactory;
import model.loader.TaxConfigPersister;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 配置持久化集成测试
 *
 * 演示如何在实际应用中使用加载器和持久化器进行配置管理。
 * 包括：加载配置、修改配置、保存配置的完整流程。
 *
 * @author GitHub Copilot
 * @version 1.0.0
 */
public class PersistenceIntegrationTest {

    /**
     * 示例1：从JSON文件加载配置并显示
     *
     * 这个示例演示如何从JSON文件加载税收配置，
     * 并显示加载后的配置信息。
     */
    @Test
    public void example1_LoadAndDisplay() throws Exception {
        System.out.println("\n=== Example 1: Load and Display Configuration ===\n");

        // Step 1: Get loader for JSON file
        TaxConfigLoader loader = TaxConfigLoaderFactory.getLoader("json");

        // Step 2: Load configuration
        TaxConfig config = loader.load();

        // Step 3: Display loaded configuration
        System.out.println("✓ Configuration loaded successfully");
        System.out.println("  Threshold: " + config.getThreshold() + " yuan");
        System.out.println("  Tax Rules: " + config.getTaxTable().getTaxRulesList().size() + " rules");

        // Display all rules
        for (TaxRule rule : config.getTaxTable().getTaxRulesList()) {
            System.out.printf("    Grade %d: %.0f - %.0f yuan @ %.2f%%\n",
                    rule.getGrade(), rule.getMin(), rule.getMax(), rule.getRate() * 100);
        }
    }

    /**
     * 示例2：修改配置并保存到新文件
     *
     * 这个示例演示如何：
     * 1. 加载现有配置
     * 2. 修改配置参数
     * 3. 保存到新文件
     */
    @Test
    public void example2_ModifyAndSave() throws Exception {
        System.out.println("\n=== Example 2: Modify and Save Configuration ===\n");

        // Step 1: Load original configuration
        TaxConfigLoader loader = TaxConfigLoaderFactory.getLoader("json");
        TaxConfig config = loader.load();
        System.out.println("✓ Original threshold: " + config.getThreshold());

        // Step 2: Modify configuration
        double newThreshold = 2500.0;
        config.setThreshold(newThreshold);
        System.out.println("✓ Modified threshold: " + config.getThreshold());

        // Step 3: Save to new file
        TaxConfigPersister persister = TaxConfigLoaderFactory.getPersister(
                "json", "backup/modified-settings.json");
        persister.save(config);
        System.out.println("✓ Configuration saved to: " + persister.getTarget());

        // Step 4: Verify file was created
        boolean fileExists = Files.exists(Paths.get("backup/modified-settings.json"));
        System.out.println("✓ File verification: " + (fileExists ? "SUCCESS" : "FAILED"));

        // Cleanup
        Files.delete(Paths.get("backup/modified-settings.json"));
        Files.delete(Paths.get("backup"));
    }

    /**
     * 示例3：创建新配置并保存
     *
     * 这个示例演示如何：
     * 1. 从零开始创建新的税收配置
     * 2. 添加税率规则
     * 3. 保存配置到文件
     */
    @Test
    public void example3_CreateNewAndSave() throws Exception {
        System.out.println("\n=== Example 3: Create New Configuration and Save ===\n");

        // Step 1: Create new tax table
        TaxTable taxTable = new TaxTable();
        System.out.println("✓ Created new TaxTable");

        // Step 2: Add tax rules
        taxTable.addTaxRule(new TaxRule(1, 0, 1000, 0.03));
        taxTable.addTaxRule(new TaxRule(2, 1000, 3000, 0.08));
        taxTable.addTaxRule(new TaxRule(3, 3000, 6000, 0.12));
        System.out.println("✓ Added 3 tax rules");

        // Step 3: Create config with custom threshold
        TaxConfig newConfig = new TaxConfig(1500.0, taxTable);
        System.out.println("✓ Created TaxConfig with threshold: " + newConfig.getThreshold());

        // Step 4: Save configuration
        TaxConfigPersister persister = TaxConfigLoaderFactory.getPersister(
                "json", "configs/custom-config.json");
        persister.save(newConfig);
        System.out.println("✓ Configuration saved to: " + persister.getTarget());

        // Step 5: Display saved content
        String content = new String(Files.readAllBytes(
                Paths.get("configs/custom-config.json")));
        System.out.println("✓ File size: " + content.length() + " bytes");

        // Cleanup
        Files.delete(Paths.get("configs/custom-config.json"));
        Files.delete(Paths.get("configs"));
    }

    /**
     * 示例4：配置备份工作流
     *
     * 这个示例演示如何实现一个简单的备份系统：
     * 1. 加载当前配置
     * 2. 创建带时间戳的备份
     * 3. 保存多个备份版本
     */
    @Test
    public void example4_BackupWorkflow() throws Exception {
        System.out.println("\n=== Example 4: Configuration Backup Workflow ===\n");

        // Step 1: Load current configuration
        TaxConfigLoader loader = TaxConfigLoaderFactory.getLoader("json");
        TaxConfig config = loader.load();
        System.out.println("✓ Loaded current configuration");

        // Step 2: Create backup directory
        Files.createDirectories(Paths.get("backups"));
        System.out.println("✓ Created backup directory");

        // Step 3: Create multiple backup versions
        String timestamp1 = "2026-03-16-v1";
        String timestamp2 = "2026-03-16-v2";

        TaxConfigPersister persister1 = TaxConfigLoaderFactory.getPersister(
                "json", "backups/tax-config-" + timestamp1 + ".json");
        TaxConfigPersister persister2 = TaxConfigLoaderFactory.getPersister(
                "json", "backups/tax-config-" + timestamp2 + ".json");

        persister1.save(config);
        persister2.save(config);

        System.out.println("✓ Created backup v1: " + persister1.getTarget());
        System.out.println("✓ Created backup v2: " + persister2.getTarget());

        // Step 4: List all backups
        File backupDir = new File("backups");
        File[] files = backupDir.listFiles();
        if (files != null) {
            System.out.println("✓ Total backups: " + files.length);
            for (File file : files) {
                System.out.println("  - " + file.getName());
            }
        }

        // Cleanup
        for (File file : backupDir.listFiles()) {
            file.delete();
        }
        backupDir.delete();
    }

    /**
     * 示例5：错误处理和验证
     *
     * 这个示例演示如何正确处理持久化过程中的错误。
     */
    @Test
    public void example5_ErrorHandling() throws Exception {
        System.out.println("\n=== Example 5: Error Handling ===\n");

        // Test 1: Null configuration handling
        try {
            TaxConfigPersister persister = TaxConfigLoaderFactory.getPersister(
                    "json", "test.json");
            persister.save(null);
            System.out.println("✗ Should have thrown exception");
        } catch (RuntimeException e) {
            System.out.println("✓ Correctly caught exception: " + e.getMessage());
        }

        // Test 2: Invalid path handling
        try {
            String invalidPath = "";
            TaxConfigLoaderFactory.getPersister("json", invalidPath);
            System.out.println("✗ Should have thrown exception");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Correctly caught exception: " + e.getMessage());
        }

        // Test 3: Successfully saving to valid path
        TaxTable taxTable = new TaxTable();
        taxTable.addTaxRule(new TaxRule(1, 0, 500, 0.05));
        TaxConfig config = new TaxConfig(1600, taxTable);

        TaxConfigPersister persister = TaxConfigLoaderFactory.getPersister(
                "json", "valid-config.json");
        persister.save(config);
        System.out.println("✓ Successfully saved to: " + persister.getTarget());

        // Verify file
        boolean exists = Files.exists(Paths.get("valid-config.json"));
        System.out.println("✓ File exists: " + exists);

        // Cleanup
        Files.delete(Paths.get("valid-config.json"));
    }
}


