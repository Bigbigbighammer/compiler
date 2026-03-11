package model.loader;

import model.entity.TaxConfig;

import java.io.File;

public class JsonFileTaxConfigLoader implements TaxConfigLoader {
    private String filePath;

    public JsonFileTaxConfigLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public TaxConfig load() throws Exception {
        System.out.println(">> [Loader] 正在从 JSON 文件加载: " + filePath);

        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("配置文件不存在: " + filePath);
        }

//        // 使用 Gson 解析
//        Gson gson = new Gson();
//        // 注意：这里可能需要创建一个中间 DTO 类来匹配 JSON 结构，
//        // 然后转换成你的 TaxConfig 和 TaxTable 对象。
//        // 为简化代码，这里假设 JSON 结构直接对应类的字段。
//        TaxConfig config = gson.fromJson(new FileReader(file), TaxConfig.class);

        return null;
    }
}
