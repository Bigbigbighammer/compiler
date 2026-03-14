# JsonFileTaxConfigLoader 实现文档

## 概述
`JsonFileTaxConfigLoader` 是一个税务配置加载器，用于从JSON文件中读取和解析税务配置信息，包括起征点和完整的税率表。

## 功能特性

### 1. 主要功能
- ✓ 从classpath中加载JSON配置文件
- ✓ 使用Gson库进行JSON解析
- ✓ 完整的数据验证和错误处理
- ✓ 将JSON数据转换为Java对象模型

### 2. 支持的配置格式
```json
{
  "threshold": 1600.0,
  "taxTable": {
    "taxRulesList": [
      {
        "grade": 1,
        "min": 0.0,
        "max": 500.0,
        "rate": 0.05
      },
      ...
    ]
  }
}
```

### 3. 数据验证
实现了以下验证规则：
- **起征点验证**: 起征点必须 >= 0
- **规则最小值验证**: min >= 0
- **规则最大值验证**: max > min
- **税率验证**: 0 <= rate <= 1

### 4. 核心方法

#### `load()` 方法
```java
@Override
public TaxConfig load() throws Exception
```
- 从指定的JSON文件加载配置
- 返回解析后的 `TaxConfig` 对象
- 抛出异常处理加载失败的情况

#### `convertToTaxConfig()` 方法
- 将JSON DTO转换为Java实体对象
- 进行数据验证
- 构建税率表和税务配置

## 使用示例

```java
// 创建加载器
TaxConfigLoader loader = new JsonFileTaxConfigLoader("settings.json");

// 加载配置
TaxConfig config = loader.load();

// 获取起征点
double threshold = config.getThreshold();

// 获取税率表
TaxTable taxTable = config.getTaxTable();
List<TaxRule> rules = taxTable.getTaxRulesList();
```

## 实现细节

### DTO类
- `JsonTaxConfigDto`: 配置数据结构
- `JsonTaxTableDto`: 税率表数据结构
- `JsonTaxRuleDto`: 单条税率规则数据结构

### 依赖
- **Gson 2.10.1**: 用于JSON序列化和反序列化

### 异常处理
- 文件不存在时抛出 `RuntimeException`
- JSON解析失败时抛出 `RuntimeException`
- 数据验证失败时抛出 `RuntimeException`

## 测试结果
✓ 所有功能测试通过
- 起征点加载正确 (1600.0元)
- 税率规则数量正确 (5条)
- 各条规则参数验证正确
- 数据转换无误

## 配置文件位置
- 默认位置: `src/main/resources/settings.json`
- classpath下: `settings.json`

## 扩展性
该实现易于扩展：
1. 可添加更多验证规则
2. 可支持其他配置源（如数据库、配置服务器等）
3. 可添加缓存机制
4. 可添加配置热更新功能

