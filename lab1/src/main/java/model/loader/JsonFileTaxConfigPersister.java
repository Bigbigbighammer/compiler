package model.loader;

import model.entity.TaxConfig;
import model.entity.TaxRule;
import model.entity.TaxTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON文件税收配置持久化实现
 *
 * 将TaxConfig对象保存到JSON格式的文件中。
 * 支持向指定路径写入配置，并使用Gson库进行序列化。
 *
 * <h2>功能特性</h2>
 * <ul>
 *   <li>将TaxConfig对象序列化为JSON格式</li>
 *   <li>支持UTF-8编码的文件写入</li>
 *   <li>自动创建父目录（如果不存在）</li>
 *   <li>支持覆盖现有文件</li>
 *   <li>详细的异常处理和错误提示</li>
 * </ul>
 *
 * <h2>使用示例</h2>
 * <pre>
 * TaxConfig config = new TaxConfig(2000, taxTable);
 * TaxConfigPersister persister = new JsonFileTaxConfigPersister("output/settings.json");
 * persister.save(config);
 * </pre>
 *
 * <h2>生成的JSON格式</h2>
 * <pre>
 * {
 *   "threshold": 2000.0,
 *   "taxTable": {
 *     "taxRulesList": [
 *       {
 *         "grade": 1,
 *         "min": 0.0,
 *         "max": 500.0,
 *         "rate": 0.05
 *       },
 *       ...
 *     ]
 *   }
 * }
 * </pre>
 *
 * @author Aaron
 * @version 1.0.0
 * @see TaxConfigPersister
 * @see TaxConfig
 * @see TaxTable
 * @see TaxRule
 */
public class JsonFileTaxConfigPersister implements TaxConfigPersister {

    /** 配置文件保存路径 */
    private final String filePath;

    /**
     * 构造函数
     *
     * @param filePath 配置文件保存路径
     *                 例如："settings.json" 或 "config/tax-config.json"
     */
    public JsonFileTaxConfigPersister(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 将税收配置保存到JSON文件
     *
     * <p>该方法执行以下步骤：</p>
     * <ol>
     *   <li>验证TaxConfig对象不为null</li>
     *   <li>创建父目录（如果不存在）</li>
     *   <li>将TaxConfig转换为JSON DTO</li>
     *   <li>使用Gson序列化为JSON格式</li>
     *   <li>写入到指定的文件路径</li>
     * </ol>
     *
     * @param config 要保存的TaxConfig对象
     * @throws RuntimeException 当保存失败时抛出异常
     * @throws Exception 其他IO异常
     * @see TaxConfig
     */
    @Override
    public void save(TaxConfig config) throws Exception {
        if (config == null) {
            throw new RuntimeException("TaxConfig cannot be null");
        }

        System.out.println(">> [Persister] Saving configuration to: " + filePath);

        try {
            // Create parent directories if they don't exist
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    throw new RuntimeException("Failed to create parent directories: " + parentDir.getAbsolutePath());
                }
                System.out.println(">> [Persister] Created directories: " + parentDir.getAbsolutePath());
            }

            // Convert TaxConfig to JSON DTO
            JsonTaxConfigDto dto = convertToDto(config);

            // Serialize to JSON using Gson
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            try (FileOutputStream fos = new FileOutputStream(file);
                 OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
                gson.toJson(dto, writer);
                System.out.println(">> [Persister] Configuration saved successfully");
            }
        } catch (RuntimeException e) {
            System.err.println("❌ 错误：配置保存失败");
            System.err.println("   文件路径: " + filePath);
            System.err.println("   详情: " + e.getMessage());
            throw new RuntimeException("Failed to save configuration to file: " + filePath, e);
        } catch (Exception e) {
            System.err.println("❌ 错误：IO异常");
            System.err.println("   文件路径: " + filePath);
            System.err.println("   详情: " + e.getMessage());
            throw new RuntimeException("IO error when saving configuration: " + filePath, e);
        }
    }

    /**
     * 获取配置文件保存路径
     *
     * @return 文件保存路径
     */
    @Override
    public String getTarget() {
        return filePath;
    }

    /**
     * 将TaxConfig实体对象转换为JSON DTO
     *
     * <p>此方法将业务实体对象转换为适合JSON序列化的DTO对象，
     * 包括所有的税率规则。</p>
     *
     * @param config TaxConfig实体对象
     * @return 转换后的JsonTaxConfigDto对象
     * @throws RuntimeException 当转换失败时抛出异常
     * @see TaxConfig
     * @see JsonTaxConfigDto
     */
    private JsonTaxConfigDto convertToDto(TaxConfig config) {
        if (config == null) {
            throw new RuntimeException("TaxConfig cannot be null");
        }

        JsonTaxConfigDto dto = new JsonTaxConfigDto();
        dto.threshold = config.getThreshold();

        // Convert TaxTable to JsonTaxTableDto
        TaxTable taxTable = config.getTaxTable();
        if (taxTable != null) {
            JsonTaxTableDto tableDto = new JsonTaxTableDto();
            tableDto.taxRulesList = new ArrayList<>();

            List<TaxRule> rules = taxTable.getTaxRulesList();
            if (rules != null) {
                for (TaxRule rule : rules) {
                    JsonTaxRuleDto ruleDto = new JsonTaxRuleDto();
                    ruleDto.grade = rule.getGrade();
                    ruleDto.min = rule.getMin();
                    ruleDto.max = rule.getMax();
                    ruleDto.rate = rule.getRate();
                    tableDto.taxRulesList.add(ruleDto);
                }
            }

            dto.taxTable = tableDto;
        }

        return dto;
    }

    /**
     * JSON配置数据传输对象
     * 用于Gson序列化TaxConfig对象到JSON
     */
    private static class JsonTaxConfigDto {
        /** 所得税起征点，单位：元 */
        public double threshold;

        /** 税率表配置 */
        public JsonTaxTableDto taxTable;
    }

    /**
     * JSON税率表数据传输对象
     * 包含多条税率规则的集合
     */
    private static class JsonTaxTableDto {
        /** 税率规则列表 */
        public List<JsonTaxRuleDto> taxRulesList = new ArrayList<>();
    }

    /**
     * JSON税率规则数据传输对象
     * 表示单条税率规则的参数
     */
    private static class JsonTaxRuleDto {
        /** 税率等级（1到5级） */
        public int grade;

        /** 收入最小值，单位：元 */
        public double min;

        /** 收入最大值，单位：元 */
        public double max;

        /** 税率，范围：0.0到1.0 */
        public double rate;
    }
}

