# 快速参考 - Quick Reference

## 📦 项目状态

✓ Git 仓库：正确配置在 `D:\Project4J\compiler`
✓ GSON 库：已添加到项目配置
✓ JSON 配置加载：完全实现
✓ 功能测试：全部通过

## 🚀 快速命令

### 编译项目
```bash
cd D:\Project4J\compiler\lab1
javac -encoding UTF-8 -cp "lib/gson-2.10.1.jar" -d bin `
  src/model/entity/*.java src/model/loader/*.java `
  src/service/*.java src/view/*.java src/Main.java
```

### 运行程序
```bash
cd D:\Project4J\compiler\lab1
java -cp "lib/gson-2.10.1.jar;bin;src/main/resources" Main
```

### 运行测试
```bash
cd D:\Project4J\compiler\lab1
javac -encoding UTF-8 -cp "lib/gson-2.10.1.jar;bin" -d bin test/JsonLoaderFunctionalTest.java
java -cp "lib/gson-2.10.1.jar;bin;src/main/resources" JsonLoaderFunctionalTest
```

## 📝 Git 快速命令

### 查看历史
```bash
cd D:\Project4J\compiler
git log --oneline -10
```

### 提交更改
```bash
cd D:\Project4J\compiler
git add .
git commit -m "描述您的更改"
```

### 推送到远程
```bash
cd D:\Project4J\compiler
git push origin master
```

## 🔧 配置文件位置

| 文件 | 路径 | 说明 |
|------|------|------|
| JSON 配置 | `lab1/src/main/resources/settings.json` | 税务配置数据 |
| 项目配置 | `lab1/lab1.iml` | IDEA 项目设置 |
| 根项目配置 | `compiler.iml` | 根 IDEA 项目设置 |
| GSON 库 | `lab1/lib/gson-2.10.1.jar` | JSON 解析库 |

## 📚 核心类

| 类 | 位置 | 功能 |
|------|------|------|
| JsonFileTaxConfigLoader | `src/model/loader/` | JSON 配置加载器 |
| TaxConfig | `src/model/entity/` | 税务配置实体 |
| TaxTable | `src/model/entity/` | 税率表 |
| TaxRule | `src/model/entity/` | 单条税率规则 |
| TaxCalculator | `src/service/` | 税费计算器 |
| TaxConsoleMenu | `src/view/` | 控制台菜单 |

## 🎯 实现要点

### ✓ 已完成

1. **JsonFileTaxConfigLoader 实现**
   - 从 classpath 加载 JSON 文件
   - 完整的数据验证
   - DTO 映射到实体对象

2. **GSON 库集成**
   - 正确配置在 `.iml` 文件中
   - 支持 UTF-8 编码
   - 自动序列化/反序列化

3. **资源文件配置**
   - `src/main/resources` 文件夹已配置
   - `settings.json` 可正确加载

4. **功能测试**
   - 起征点加载测试 ✓
   - 税率规则加载测试 ✓
   - 数据验证测试 ✓

### 🔮 后续改进方向

1. 添加配置热更新
2. 支持多个配置源
3. 添加配置文件加密
4. 实现配置版本管理

## 📞 故障排除

### 问题：IDEA 显示 GSON 错误
**解决**：右键项目 → File → Reload Project

### 问题：编译错误 - Cannot resolve symbol
**解决**：确保 GSON jar 文件存在，检查 classpath 配置

### 问题：JSON 文件找不到
**解决**：确保 `src/main/resources` 在编译时被包含到 classpath

### 问题：中文显示乱码
**解决**：添加 `-encoding UTF-8` 编译参数

## 💾 文件清单

```
compiler/
├── .git/                          # Git 仓库
├── .gitignore                     # Git 忽略配置
├── compiler.iml                   # 根项目配置
├── PROJECT_GUIDE.md               # 详细指南
├── QUICK_REFERENCE.md             # 本文件
└── lab1/
    ├── lab1.iml                   # Lab1 项目配置
    ├── bin/                       # 编译输出
    ├── lib/
    │   └── gson-2.10.1.jar        # GSON 库
    ├── src/
    │   ├── Main.java
    │   ├── main/resources/
    │   │   └── settings.json
    │   ├── model/
    │   │   ├── entity/
    │   │   │   ├── TaxConfig.java
    │   │   │   ├── TaxRule.java
    │   │   │   └── TaxTable.java
    │   │   └── loader/
    │   │       ├── DefaultTaxConfigLoader.java
    │   │       ├── JsonFileTaxConfigLoader.java  ✓ 已实现
    │   │       ├── TaxConfigLoader.java
    │   │       └── TaxConfigLoaderFactory.java
    │   ├── service/
    │   │   ├── BaseTaxHandler.java
    │   │   ├── TaxCalculator.java
    │   │   ├── TaxChain.java
    │   │   ├── TaxContext.java
    │   │   └── TaxHandler.java
    │   └── view/
    │       └── TaxConsoleMenu.java
    ├── test/
    │   ├── BasicTaxTest.java
    │   ├── JsonLoaderTest.java
    │   ├── JsonLoaderFunctionalTest.java  ✓ 新增
    │   ├── RegressionTest.java
    │   └── lib/
    │       ├── junit-jupiter-api-5.9.2.jar
    │       ├── junit-jupiter-engine-5.9.2.jar
    │       └── junit-platform-console-standalone-1.9.2.jar
    └── JSON_LOADER_DOCUMENTATION.md       ✓ 新增
```

## 🔐 Git 配置

```bash
# 查看当前配置
git config user.name     # Developer
git config user.email    # developer@example.com

# 修改配置
git config user.name "Your Name"
git config user.email "your.email@example.com"
```

## 📊 测试结果

最近一次提交：`60536969efe3fc799c5d0bb9616f7e0d5957649d`

```
完成JSON配置加载器实现
- 实现JsonFileTaxConfigLoader用于加载JSON格式的税务配置
- 添加完整的数据验证（起征点、税率规则参数）
- 支持从classpath加载settings.json文件
- 使用Gson库进行JSON序列化和反序列化
- 添加功能测试验证所有功能正常工作
- 更新项目配置支持GSON库和资源文件夹
- 所有税率规则正确解析和加载
```

## 🎓 使用示例

### 加载配置
```java
TaxConfigLoader loader = new JsonFileTaxConfigLoader("settings.json");
TaxConfig config = loader.load();
System.out.println("起征点: " + config.getThreshold());
```

### 获取税率表
```java
TaxTable taxTable = config.getTaxTable();
List<TaxRule> rules = taxTable.getTaxRulesList();
for (TaxRule rule : rules) {
    System.out.println(rule.getRange() + ": " + rule.getRate() * 100 + "%");
}
```

---

**最后更新**: 2026-03-14 22:31:59
**状态**: ✓ 完成

