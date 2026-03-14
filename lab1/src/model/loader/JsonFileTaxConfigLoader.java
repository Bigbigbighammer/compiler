package model.loader;

import model.entity.TaxConfig;
import model.entity.TaxRule;
import model.entity.TaxTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON文件税收配置加载器
 *
 * 从classpath中加载JSON格式的税务配置文件，并使用Gson库进行解析。
 * 支持从settings.json文件中读取起征点和税率表的完整配置。
 *
 * <h2>功能特性</h2>
 * <ul>
 *   <li>从classpath资源加载JSON配置文件</li>
 *   <li>支持UTF-8编码的JSON文件</li>
 *   <li>完整的数据验证（起征点、税率规则参数）</li>
 *   <li>自动转换JSON DTO到业务实体对象</li>
 *   <li>详细的异常处理和错误提示</li>
 * </ul>
 *
 * <h2>使用示例</h2>
 * <pre>
 * TaxConfigLoader loader = new JsonFileTaxConfigLoader("settings.json");
 * TaxConfig config = loader.load();
 * double threshold = config.getThreshold();
 * </pre>
 *
 * <h2>配置文件位置</h2>
 * <p>settings.json 文件位于项目根目录 (lab1/) 下，
 * 与源代码目录 (src/) 同级。</p>
 *
 * <h2>支持的JSON格式</h2>
 * <pre>
 * {
 *   "threshold": 1600.0,
 *   "taxTable": {
 *     "taxRulesList": [
 *       {"grade": 1, "min": 0.0, "max": 500.0, "rate": 0.05},
 *       ...
 *     ]
 *   }
 * }
 * </pre>
 *
 * @author GitHub Copilot
 * @version 1.0.0
 * @see TaxConfigLoader
 * @see TaxConfig
 * @see TaxTable
 * @see TaxRule
 */
public class JsonFileTaxConfigLoader implements TaxConfigLoader {

    /** 配置文件路径 */
    private final String filePath;

    /**
     * 构造函数
     *
     * @param filePath 配置文件路径（相对于classpath）
     *                 例如："settings.json"
     */
    public JsonFileTaxConfigLoader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 从JSON配置文件加载税务配置
     *
     * <p>该方法执行以下步骤：
     * <ol>
     *   <li>从classpath加载指定的JSON文件</li>
     *   <li>使用Gson库解析JSON内容</li>
     *   <li>验证所有配置数据的有效性</li>
     *   <li>将JSON DTO转换为TaxConfig实体对象</li>
     * </ol>
     * </p>
     *
     * @return 解析后的TaxConfig对象
     * @throws RuntimeException 当文件不存在、JSON解析失败或数据验证失败时抛出
     * @throws Exception 其他IO异常
     *
     * @see TaxConfig
     */
    @Override
    public TaxConfig load() throws Exception {
        System.out.println(">> [Loader] Loading from JSON file: " + filePath);

        // Load from classpath resource
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new RuntimeException("Configuration file not found in classpath: " + filePath);
        }

        // Parse JSON using Gson
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (InputStream is = inputStream;
             Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            JsonTaxConfigDto dto = gson.fromJson(reader, JsonTaxConfigDto.class);
            return convertToTaxConfig(dto);
        }
    }

    /**
     * 将JSON DTO转换为TaxConfig实体对象
     *
     * <p>此方法进行���下验证：
     * <ul>
     *   <li>验证起征点不为负数</li>
     *   <li>验证每条税率规则的参数有效性</li>
     *   <li>验证规则的最大值大于最小值</li>
     *   <li>验证税率在0到1之间</li>
     * </ul>
     * </p>
     *
     * @param dto JSON DTO对象
     * @return 转换后的TaxConfig对象
     * @throws RuntimeException 当数据验证失败时抛出异常
     *
     * @see JsonTaxConfigDto
     * @see TaxConfig
     */
    private TaxConfig convertToTaxConfig(JsonTaxConfigDto dto) {
        if (dto == null) {
            throw new RuntimeException("Failed to parse JSON configuration");
        }

        // Validate threshold
        if (dto.threshold < 0) {
            throw new RuntimeException("Threshold cannot be negative: " + dto.threshold);
        }

        // Convert DTO to TaxTable
        TaxTable taxTable = new TaxTable();
        List<TaxRule> taxRules = new ArrayList<>();

        if (dto.taxTable != null && dto.taxTable.taxRulesList != null) {
            for (JsonTaxRuleDto ruleDto : dto.taxTable.taxRulesList) {
                // Validate rule
                if (ruleDto.min < 0) {
                    throw new RuntimeException("Rule min cannot be negative: " + ruleDto.min);
                }
                if (ruleDto.max <= ruleDto.min) {
                    throw new RuntimeException("Rule max must be greater than min: " + ruleDto.max + " <= " + ruleDto.min);
                }
                if (ruleDto.rate < 0 || ruleDto.rate > 1) {
                    throw new RuntimeException("Rule rate must be between 0 and 1: " + ruleDto.rate);
                }

                TaxRule rule = new TaxRule(
                    ruleDto.grade,
                    ruleDto.min,
                    ruleDto.max,
                    ruleDto.rate
                );
                taxRules.add(rule);
            }
        }

        taxTable.resetRules(taxRules);

        // Create and return TaxConfig
        return new TaxConfig(dto.threshold, taxTable);
    }

    /**
     * JSON配置数据传输对象
     * 用于Gson反序列化JSON配置文件
     */
    private static class JsonTaxConfigDto {
        /** 所得税起征点，单位：元 */
        double threshold;

        /** 税率表配置 */
        JsonTaxTableDto taxTable;
    }

    /**
     * JSON税率表数据传输对象
     * 包含多条税率规则的集合
     */
    private static class JsonTaxTableDto {
        /** 税率规则列表 */
        List<JsonTaxRuleDto> taxRulesList = new ArrayList<>();
    }

    /**
     * JSON税率规则数据传输对象
     * 表示单条税率规则的参数
     */
    private static class JsonTaxRuleDto {
        /** 税率等级（1到5级） */
        int grade;

        /** 收入最小值，单位：元 */
        double min;

        /** 收入最大值，单位：元 */
        double max;

        /** 税率，范围：0.0到1.0 */
        double rate;
    }
}
