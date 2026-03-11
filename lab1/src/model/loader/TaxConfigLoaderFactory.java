package model.loader;

public class TaxConfigLoaderFactory {

    public static TaxConfigLoader getLoader(String type) {
        switch (type.toLowerCase()) {
            case "json":
                return new JsonFileTaxConfigLoader("config.json");
//            case "redis":
//                return new RedisTaxConfigLoader("localhost", 6379, "tax_config");
            case "default":
            default:
                return new DefaultTaxConfigLoader();
        }
    }
}