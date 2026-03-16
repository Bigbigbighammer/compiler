快速启动指南 - 个人所得税计算系统
====================================

## 📋 脚本概览

我已为您生成了 4 个启动脚本，用于在不同环境下启动项目和运行测试：

| 脚本 | 系统 | 需求 | 功能 |
|------|------|------|------|
| run-with-maven.bat | Windows | Maven | ✅ 完整（编译、测试、运行） |
| run-with-maven.sh | Linux/Mac | Maven | ✅ 完整（编译、测试、运行） |
| run-without-maven.bat | Windows | 仅Java | ⚠️ 仅运行已编译项目 |
| run-without-maven.sh | Linux/Mac | 仅Java | ⚠️ 仅运行已编译项目 |

---

## 🚀 快速开始（有 Maven）

### Windows
```bash
run-with-maven.bat
```

### Linux/Mac
```bash
chmod +x run-with-maven.sh
./run-with-maven.sh
```

然后选择菜单选项：
- **1**: 编译项目
- **2**: 运行单元测试
- **3**: 完整编译+测试
- **4**: 打包为 JAR
- **5**: 启动应用程序

---

## 🔧 快速开始（无 Maven）

### 前置步骤（在有 Maven 的机器上）
```bash
mvn clean package
```

### 然后在无 Maven 的机器上

**Windows**
```bash
run-without-maven.bat
```

**Linux/Mac**
```bash
chmod +x run-without-maven.sh
./run-without-maven.sh
```

---

## 📚 详细文档

完整的使用指南请查看：**SCRIPT_USAGE_GUIDE.md**

包含以下内容：
- 前置条件检查
- 详细的菜单选项说明
- 命令行参考
- 故障排除指南
- 最佳实践

---

## ⚙️ 核心命令参考

如果您不想使用脚本，也可以直接运行这些 Maven 命令：

```bash
# 编译项目（跳过测试）
mvn clean install -DskipTests

# 运行所有单元测试
mvn test

# 完整构建（编译+测试+打包）
mvn clean install

# 打包为可执行 JAR
mvn clean package

# 启动应用程序（打包后）
java -jar target/tax-calculator.jar
```

---

## 📂 项目结构说明

```
├── run-with-maven.bat          # Windows + Maven 脚本
├── run-with-maven.sh           # Linux/Mac + Maven 脚本
├── run-without-maven.bat       # Windows + 无Maven 脚本
├── run-without-maven.sh        # Linux/Mac + 无Maven 脚本
├── SCRIPT_USAGE_GUIDE.md       # 详细使用指南
├── QUICKSTART.md               # 快速开始指南（本文件）
├── pom.xml                     # Maven 配置文件
├── settings.json               # 项目配置文件
├── src/
│   ├── main/java/              # 源代码
│   └── test/java/              # 单元测试
└── target/                     # 编译输出目录
    ├── classes/                # 编译后的类
    ├── test-classes/           # 编译后的测试
    ├── tax-calculator.jar      # 可执行 JAR（打包后）
    └── surefire-reports/       # 测试报告
```

---

## ✨ 脚本特性

✅ **自动依赖检查** - 验证 Java 和 Maven 环境
✅ **菜单驱动** - 友好的交互式界面
✅ **错误处理** - 自动捕获和报告错误
✅ **彩色输出** - 在 Linux/Mac 上显示彩色信息
✅ **循环菜单** - 无需重启脚本进行多次操作
✅ **完整帮助** - 内置帮助信息和提示

---

## 🧪 单元测试

项目包含 6 个测试类：
- BasicTaxTest.java
- JsonLoaderTest.java
- JsonLoaderFunctionalTest.java
- PersistenceTest.java
- PersistenceIntegrationTest.java
- RegressionTest.java

运行测试后，报告位置：
```
target/surefire-reports/
```

---

## 💡 常见问题

**Q: 我应该使用哪个脚本？**
A: 如果有 Maven，使用 `run-with-maven.*`。否则使用 `run-without-maven.*`。

**Q: 脚本无法运行？**
A: 在 Linux/Mac 上，需要先执行 `chmod +x run-with-maven.sh`

**Q: 如何在无 Maven 环境运行测试？**
A: 需要先在有 Maven 的机器上执行 `mvn clean test`，然后复制整个项目文件夹。

**Q: 可以同时打开多个脚本实例吗？**
A: 可以，但建议在同一脚本中使用菜单来执行多个操作。

---

## 📞 支持

如有问题，请查看：
1. SCRIPT_USAGE_GUIDE.md 中的故障排除部分
2. 项目文档：design.md, PERSISTENCE_GUIDE.md 等

---

最后更新：2026-03-16

