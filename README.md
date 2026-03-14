# 税收计算系统 - Tax Calculation System

一个基于Java的税收计算系统，支持JSON配置文件、灵活的税率表管理和完整的税费计算功能。

## 📋 快速概览

- ✅ **JSON配置加载** - 从JSON文件加载税务配置
- ✅ **灵活的税率表** - 支持多条税率规则
- ✅ **税费计算** - 准确的个人所得税计算
- ✅ **交互式菜单** - 友好的控制台交互界面
- ✅ **完整的测试** - 单元测试和功能测试
- ✅ **Git版本控制** - 完整的版本历史管理

## 🚀 快速开始

### 前提条件
- Java 8 或更高版本
- Gson 2.10.1 库（已包含在 `lab1/lib` 目录中）

### 编译项目

```bash
cd lab1
javac -encoding UTF-8 -cp "lib/gson-2.10.1.jar" -d bin `
  src/model/entity/*.java `
  src/model/loader/*.java `
  src/service/*.java `
  src/view/*.java `
  src/Main.java
```

### 运行程序

```bash
cd lab1
java -cp "lib/gson-2.10.1.jar;bin;src/main/resources" Main
```

### 运行测试

```bash
cd lab1
javac -encoding UTF-8 -cp "lib/gson-2.10.1.jar;bin" -d bin test/JsonLoaderFunctionalTest.java
java -cp "lib/gson-2.10.1.jar;bin;src/main/resources" JsonLoaderFunctionalTest
```

## 📁 项目结构

```
compiler/
├── .git/                              # Git 版本控制
├── .gitignore                         # Git 忽略配置
├── compiler.iml                       # 根项目配置
├── README.md                          # 本文件
├── PROJECT_GUIDE.md                   # 详细指南
├── QUICK_REFERENCE.md                 # 快速参考
├── COMPLETION_SUMMARY.md              # 完成总结
│
└── lab1/                              # Lab1 子项目
    ├── lab1.iml                       # 项目配置
    ├── JSON_LOADER_DOCUMENTATION.md  # JSON加载器文档
    │
    ├── src/                           # 源代码
    │   ├── Main.java                  # 入口类
    │   ├── main/resources/
    │   │   └── settings.json          # 税务配置文件
    │   │
    │   ├── model/                     # 数据模型
    │   │   ├── entity/
    │   │   │   ├── TaxConfig.java     # 税务配置
    │   │   │   ├── TaxRule.java       # 税率规则
    │   │   │   └── TaxTable.java      # 税率表
    │   │   └── loader/
    │   │       ├── TaxConfigLoader.java           # 加载器接口
    │   │       ├── JsonFileTaxConfigLoader.java   # JSON加载器 ✓
    │   │       ├── DefaultTaxConfigLoader.java    # 默认加载器
    │   │       └── TaxConfigLoaderFactory.java    # 工厂类
    │   │
    │   ├── service/                   # 业务逻辑
    │   │   ├── TaxCalculator.java     # 税费计算器
    │   │   ├── TaxChain.java          # 处理链
    │   │   ├── TaxContext.java        # 上下文
    │   │   ├── TaxHandler.java        # 处理器接口
    │   │   └── BaseTaxHandler.java    # 基础处理器
    │   │
    │   └── view/                      # 视图层
    │       └── TaxConsoleMenu.java    # 控制台菜单
    │
    ├── test/                          # 测试文件
    │   ├── BasicTaxTest.java          # 基础测试
    │   ├── JsonLoaderTest.java        # JSON加载器测试
    │   ├── JsonLoaderFunctionalTest.java # 功能测试 ✓
    │   ├── RegressionTest.java        # 回归测试
    │   └── lib/                       # 测试库
    │       ├── junit-jupiter-api-5.9.2.jar
    │       ├── junit-jupiter-engine-5.9.2.jar
    │       └── junit-platform-console-standalone-1.9.2.jar
    │
    ├── lib/                           # 项目依赖
    │   └── gson-2.10.1.jar            # JSON解析库
    │
    └── bin/                           # 编译输出目录
        └── (编译后的class文件)
```

## ✨ 核心功能

### 1. JSON配置加载
- 从 `settings.json` 加载税务配置
- 支持起征点（threshold）配置
- 支持多条税率规则
- 完整的数据验证

### 2. 税率规则管理
- 定义税率等级（grade）
- 设置收入范围（min/max）
- 指定适用税率（rate）
- 灵活的规则组合

### 3. 税费计算
- 精确的个人所得税计算
- 支持动态税率调整
- 完整的链式处理
- 可扩展的计算框架

### 4. 交互式菜单
- 计算个人所得税
- 设置新的起征点
- 重置完整的税率表
- 调整现有税率

## 📖 使用示例

### 加载配置并计算税费

```java
// 创建加载器
TaxConfigLoader loader = new JsonFileTaxConfigLoader("settings.json");

// 加载配置
TaxConfig config = loader.load();
System.out.println("起征点: " + config.getThreshold() + " 元");

// 计算税费
double salary = 2000;
TaxCalculator calculator = new TaxCalculator(config);
double tax = calculator.calculateTax(salary);
System.out.println("应缴税: " + tax + " 元");
```

### 查看税率表

```java
TaxTable taxTable = config.getTaxTable();
List<TaxRule> rules = taxTable.getTaxRulesList();

for (TaxRule rule : rules) {
    System.out.println(rule.getRange() + ": " + rule.getRate() * 100 + "%");
}
```

## 🔧 配置文件格式

`src/main/resources/settings.json`:

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
      {
        "grade": 2,
        "min": 500.0,
        "max": 2000.0,
        "rate": 0.10
      },
      {
        "grade": 3,
        "min": 2000.0,
        "max": 5000.0,
        "rate": 0.15
      },
      {
        "grade": 4,
        "min": 5000.0,
        "max": 20000.0,
        "rate": 0.20
      },
      {
        "grade": 5,
        "min": 20000.0,
        "max": 1.7976931348623157E308,
        "rate": 0.25
      }
    ]
  }
}
```

## 🧪 测试

### 功能测试结果

```
✓ 起征点: 1600.0 元
✓ 税率规则数量: 5 条
✓ 第一条规则: 等级=1, 最小=0.0, 最大=500.0, 税率=0.05
✓ 最后一条规则: 等级=5, 最小=20000.0, 最大=1.7976931348623157E308, 税率=0.25
✓ 所有测试通过！JSON配置加载功能正常。
```

## 📦 依赖库

- **Gson 2.10.1** - JSON 序列化/反序列化
- **JUnit 5.9.2** - 单元测试框架
- **JUnit Platform 1.9.2** - 测试平台

## 🔐 Git 版本控制

### 查看历史

```bash
cd compiler
git log --oneline              # 查看提交历史
git log -1                     # 查看最近提交
```

### 推送到远程

```bash
cd compiler

# 首次配置远程仓库
git remote add origin https://github.com/username/Project4J-compiler.git

# 推送代码
git push -u origin master
```

## 📚 文档

所有项目文档位于 `lab1/` 目录下：

- **lab1/README.md** - 项目概览和快速开始（本文件的原始版本）
- **lab1/PROJECT_GUIDE.md** - 详细的项目配置和使用指南
- **lab1/QUICK_REFERENCE.md** - 快速参考和常用命令
- **lab1/COMPLETION_SUMMARY.md** - 项目完成总结
- **lab1/FINAL_COMPLETION_REPORT.md** - 最终完成报告
- **lab1/PROJECT_COMPLETION_REPORT.xml** - XML格式的完成报告
- **lab1/JSON_LOADER_DOCUMENTATION.md** - JSON加载器技术文档
- **lab1/JAVADOC_GENERATION_SUMMARY.md** - javadoc生成总结
- **lab1/docs/index.html** - 自动生成的HTML API文档（打开此文件查看完整的API文档）

## 📖 查看javadoc文档

1. ✅ **完整的Java项目结构** - 遵循Maven标准目录布局
2. ✅ **专业的配置管理** - JSON配置加载和验证
3. ✅ **设计模式应用** - 工厂模式、链模式、策略模式
4. ✅ **完善的测试覆盖** - 单元测试和功能测试
5. ✅ **版本控制管理** - Git完整的提交历史
6. ✅ **文档完善** - 详细的使用和开发文档

## 🚀 后续改进

- [ ] 添加数据库持久化
- [ ] 实现配置热更新
- [ ] 支持多个配置源
- [ ] 添加性能优化
- [ ] 完善错误处理

## 🤝 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 📝 许可证

本项目采用 MIT 许可证。详见 LICENSE 文件。

## 👤 作者

- **Developer** - 初始工作
- **GitHub Copilot** - 代码优化和文档完善

## 🔗 相关链接

- [Gson GitHub](https://github.com/google/gson)
- [JUnit 5](https://junit.org/junit5/)
- [Git 官网](https://git-scm.com/)

## 📞 常见问题

### Q: 如何修改税率规则？
A: 编辑 `src/main/resources/settings.json` 文件，在 `taxRulesList` 中添加或修改规则。

### Q: 如何在IDEA中正确加载项目？
A: 右键项目 → File → Reload Project，确保所有配置被正确加载。

### Q: 如何添加新的功能模块？
A: 在 `src` 目录下创建新的包和类，遵循现有的项目结构。

### Q: 如何运行所有测试？
A: 在 `lab1/test` 目录下使用 `run_all_tests.bat` 或手动编译运行每个测试类。

---

**项目状态**: ✅ 完成
**最后更新**: 2026-03-14
**版本**: 1.0.0

