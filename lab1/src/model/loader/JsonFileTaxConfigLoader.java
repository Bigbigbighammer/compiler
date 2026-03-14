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

public class JsonFileTaxConfigLoader implements TaxConfigLoader {
    private final String filePath;

    public JsonFileTaxConfigLoader(String filePath) {
        this.filePath = filePath;
    }

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

    // DTO classes for JSON parsing
    private static class JsonTaxConfigDto {
        double threshold;
        JsonTaxTableDto taxTable;
    }

    private static class JsonTaxTableDto {
        List<JsonTaxRuleDto> taxRulesList = new ArrayList<>();
    }

    private static class JsonTaxRuleDto {
        int grade;
        double min;
        double max;
        double rate;
    }
}
