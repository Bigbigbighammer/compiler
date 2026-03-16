# 脚本使用指南

个人所得税计算系统提供了四个启动脚本，用于在有或没有 Maven 环境的情况下启动项目和运行测试。

## 脚本列表

| 脚本名称 | 操作系统 | Maven 环境 | 说明 |
|---------|---------|----------|------|
| `run-with-maven.bat` | Windows | 有 Maven | 完整功能，推荐使用 |
| `run-with-maven.sh` | Linux/Mac | 有 Maven | 完整功能，推荐使用 |
| `run-without-maven.bat` | Windows | 无 Maven | 仅运行已编译的项目 |
| `run-without-maven.sh` | Linux/Mac | 无 Maven | 仅运行已编译的项目 |

---

## 一、有 Maven 环境的情况（推荐）

### 前置条件
- 已安装 JDK 8 或更高版本
- 已安装 Maven 3.6 或更高版本
- 配置了 `JAVA_HOME` 和 `MAVEN_HOME` 环境变量

### Windows 系统

```bash
# 在项目根目录下运行
run-with-maven.bat
```

### Linux/Mac 系统

```bash
# 在项目根目录下运行
chmod +x run-with-maven.sh  # 第一次使用时需要赋予执行权限
./run-with-maven.sh
```

### 菜单选项

1. **清理项目并编译** (`clean install`)
   - 清理旧的编译结果
   - 重新编译源代码
   - 跳过测试以节省时间

2. **执行所有单元测试** (`test`)
   - 运行所有单元测试
   - 生成测试报告到 `target/surefire-reports/`

3. **清理 + 编译 + 测试** (`clean install`)
   - 完整的构建流程
   - 包括编译、单元测试、打包等所有步骤

4. **打包项目** (`package`)
   - 生成可执行的 JAR 文件
   - 输出文件：`target/tax-calculator.jar`

5. **启动应用程序**
   - 启动交互式菜单程序
   - 支持计算个人所得税、修改参数等功能

6. **清理构建目录** (`clean`)
   - 删除 `target` 目录
   - 清除所有编译和测试输出

7. **退出**

---

## 二、没有 Maven 环境的情况

### 前置条件
- 已安装 JDK 8 或更高版本
- 已在有 Maven 的机器上编译和打包项目
- 已将编译后的文件复制到当前机器

### 准备步骤（在有 Maven 的机器上）

```bash
# 1. 编译项目
mvn clean install

# 2. 打包项目
mvn clean package

# 3. 运行测试
mvn clean test
```

然后将整个项目文件夹复制到没有 Maven 的机器上。

### Windows 系统

```bash
# 在项目根目录下运行
run-without-maven.bat
```

### Linux/Mac 系统

```bash
# 在项目根目录下运行
chmod +x run-without-maven.sh  # 第一次使用时需要赋予执行权限
./run-without-maven.sh
```

### 菜单选项

1. **启动应用程序**
   - 使用预编译的 JAR 文件或类文件
   - 启动交互式菜单程序

2. **执行单元测试**
   - 显示测试文件的位置
   - 建议在有 Maven 环境的机器上运行测试

3. **查看帮助信息**

4. **退出**

### 项目结构要求

以下目录结构必须存在：

```
target/
├── classes/                      # 编译后的应用程序类
├── test-classes/                 # 编译后的测试类
├── lib/                          # 依赖的 JAR 文件（可选）
├── tax-calculator.jar           # 可执行的 JAR 文件（可选）
└── surefire-reports/            # 测试报告
```

---

## 三、快速命令参考

### 仅有编译需要
```bash
# Windows
mvn clean install -DskipTests

# Linux/Mac
mvn clean install -DskipTests
```

### 仅运行测试
```bash
# Windows & Linux/Mac
mvn test
```

### 完整构建（编译+测试+打包）
```bash
# Windows & Linux/Mac
mvn clean install
```

### 生成可执行 JAR
```bash
# Windows & Linux/Mac
mvn clean package
```

### 运行应用程序（需要 JAR 文件）
```bash
# Windows
java -jar target\tax-calculator.jar

# Linux/Mac
java -jar target/tax-calculator.jar
```

---

## 四、故障排除

### 问题 1：找不到 Maven
**症状**：`Maven 未找到或未在 PATH 中配置`

**解决方案**：
- 确认已安装 Maven
- 设置 `MAVEN_HOME` 环境变量
- 将 `%MAVEN_HOME%\bin` 添加到 `PATH`

### 问题 2：找不到 Java
**症状**：`Java 未找到或未在 PATH 中配置`

**解决方案**：
- 确认已安装 JDK（不是 JRE）
- 设置 `JAVA_HOME` 环境变量
- 将 `%JAVA_HOME%\bin` 添加到 `PATH`

### 问题 3：编译失败
**症状**：编译时出现错误

**解决方案**：
- 检查 Java 版本是否为 1.8 或更高
- 运行 `mvn clean install` 重新编译
- 查看错误日志了解详细信息

### 问题 4：测试失败
**症状**：单元测试失败

**解决方案**：
- 查看 `target/surefire-reports/` 目录中的测试报告
- 检查项目配置文件 `settings.json`
- 确保所有依赖都已正确下载

### 问题 5：无法启动应用程序
**症状**：启动时出现 `找不到可执行文件`

**解决方案**：
- 确保已执行编译或打包步骤
- 检查 `target/classes` 或 `target/tax-calculator.jar` 是否存在
- 在 Maven 环境中重新执行 `mvn package`

---

## 五、项目配置文件说明

### settings.json
- 位置：项目根目录
- 用途：配置税率表、起征点等参数
- 格式：JSON

### pom.xml
- 位置：项目根目录
- 用途：Maven 项目配置文件
- 包含：依赖管理、编译配置、插件配置等

---

## 六、测试报告位置

测试执行后，可在以下位置查看报告：

```
target/surefire-reports/
├── BasicTaxTest.txt
├── JsonLoaderTest.txt
├── PersistenceIntegrationTest.txt
├── PersistenceTest.txt
├── RegressionTest.txt
├── TEST-BasicTaxTest.xml
├── TEST-JsonLoaderTest.xml
├── TEST-PersistenceIntegrationTest.xml
├── TEST-PersistenceTest.xml
└── TEST-RegressionTest.xml
```

---

## 七、最佳实践

### 1. 首次运行
```bash
# 有 Maven 环境
mvn clean install      # 完整编译和测试

# 启动应用程序
运行脚本，选择"启动应用程序"
```

### 2. 日常开发
```bash
# 快速编译（跳过测试）
mvn clean install -DskipTests

# 运行测试
mvn test
```

### 3. 发布版本
```bash
# 完整构建
mvn clean install

# 打包
mvn clean package
```

### 4. 在无 Maven 环境发布
```bash
# 在 Maven 环境中打包
mvn clean package

# 复制整个项目到目标机器
# 使用 run-without-maven 脚本启动
```

---

## 八、脚本特性

- ✅ 菜单驱动，易于使用
- ✅ 自动检查依赖（Java、Maven）
- ✅ 彩色输出（Linux/Mac）
- ✅ 错误提示和恢复
- ✅ 支持多次操作而无需重新启动脚本
- ✅ 完整的帮助信息

---

## 许可证

本项目采用 Apache License 2.0 许可证。


