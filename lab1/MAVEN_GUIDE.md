================================================================================
                    Maven 配置和使用指南
================================================================================

【项目信息】
================================================================================
项目名称: Personal Income Tax Calculator (tax-calculator)
GroupId: com.tax
ArtifactId: tax-calculator
版本: 1.0.0
Java版本: 1.8+

【前置条件】
================================================================================
✓ Maven 3.6+ 已安装
✓ Java 1.8+ 已安装

检查安装:
  mvn --version
  java -version

如果未安装Maven:
  https://maven.apache.org/download.cgi

【基本命令】
================================================================================

1. 清理构建目录
   mvn clean

2. 编译源代码
   mvn compile

3. 运行单元测试
   mvn test

4. 打包项目（生成jar文件）
   mvn package

5. 生成Javadoc文档
   mvn javadoc:javadoc

6. 完整的构建流程（清理+编译+测试+打包）
   mvn clean package

7. 安装到本地Maven仓库
   mvn install

8. 跳过测试进行打包
   mvn clean package -DskipTests

【依赖管理】
================================================================================

项目依赖:

1. Gson 2.10.1 (JSON处理)
   - 用途: 解析settings.json配置文件
   - 作用域: compile（编译和运行都需要）

2. JUnit 5.9.2 (单元测试)
   - 用途: 编写和运行测试用例
   - 作用域: test（仅在测试时使用）

【项目结构】
================================================================================

税收计算系统（Maven标准结构）
│
├── pom.xml                          ← Maven配置文件
├── src/
│   ├── Main.java
│   ├── model/
│   │   ├── entity/
│   │   │   ├── TaxConfig.java
│   │   │   ├── TaxRule.java
│   │   │   └── TaxTable.java
│   │   └── loader/
│   │       ├── TaxConfigLoader.java
│   │       ├── JsonFileTaxConfigLoader.java
│   │       ├── DefaultTaxConfigLoader.java
│   │       └── TaxConfigLoaderFactory.java
│   ├── service/
│   │   ├── TaxCalculator.java
│   │   ├── TaxChain.java
│   │   ├── TaxContext.java
│   │   ├── TaxHandler.java
│   │   └── BaseTaxHandler.java
│   └── view/
│       └── TaxConsoleMenu.java
│
├── test/
│   ├── BasicTaxTest.java
│   ├── JsonLoaderTest.java
│   ├── JsonLoaderFunctionalTest.java
│   └── RegressionTest.java
│
├── target/                          ← Maven生成的输出目录
│   ├── classes/                     （编译后的class文件）
│   ├── tax-calculator-1.0.0.jar     （生成的jar包）
│   └── ...
│
└── settings.json                    ← 配置文件

【常见工作流】
================================================================================

【开发流程】

1. 编译代码
   mvn compile

2. 开发后立即测试
   mvn test

3. 完整验证
   mvn clean test

4. 打包发布
   mvn clean package

【生成文档】

1. 生成Javadoc文档
   mvn javadoc:javadoc
   输出位置: target/site/apidocs/

2. 生成源代码包
   mvn source:jar

3. 同时生成jar、javadoc、源代码
   mvn clean package source:jar javadoc:jar

【发布到远程仓库】

1. 生成所有文件
   mvn clean package source:jar javadoc:jar

2. 部署到仓库
   mvn deploy

【运行程序】
================================================================================

使用Maven运行程序:

1. 编译并打包
   mvn clean package -DskipTests

2. 运行jar文件
   java -cp target/tax-calculator-1.0.0.jar Main

3. 运行可执行的fat jar（包含所有依赖）
   mvn clean assembly:single
   java -jar target/tax-calculator-1.0.0-jar-with-dependencies.jar

【调试和排查】
================================================================================

1. 查看依赖树
   mvn dependency:tree

2. 显示详细的构建输出
   mvn clean package -X

3. 清理本地缓存
   mvn clean

4. 更新所有依赖
   mvn dependency:resolve

5. 检查过期的依赖
   mvn versions:display-dependency-updates

【IDE集成】
================================================================================

IDEA集成Maven:

1. File → Open → 选择包含pom.xml的目录
2. IDEA会自动识别Maven项目
3. 在IDEA中可以直接运行Maven命令:
   - 右键项目 → Maven → Reimport
   - 右键项目 → Run Anything (Ctrl+Ctrl) → 输入maven命令

Eclipse集成Maven:

1. 右键项目 → Configure → Convert to Maven Project
2. 或 File → New → Other → Maven Project

【常见问题】
================================================================================

Q1: 如何添加新的依赖?
A: 编辑pom.xml，在<dependencies>中添加新的<dependency>，然后运行:
   mvn dependency:resolve

Q2: jar包太大了怎么办?
A: 不使用assembly插件，只打包代码:
   mvn clean package

Q3: 如何跳过测试?
A: mvn clean package -DskipTests
   或: mvn clean package -Dmaven.test.skip=true

Q4: 依赖冲突怎么解决?
A: mvn dependency:tree 查看依赖树
   在pom.xml中添加<exclusions>排除冲突的依赖

【Maven生命周期】
================================================================================

Maven默认生命周期的主要阶段（按顺序执行）:

1. validate      - 验证项目是否正确
2. compile       - 编译源代码
3. test          - 运行单元测试
4. package       - 打包编译后的代码（jar、war等）
5. install       - 安装到本地仓库
6. deploy        - 上传到远程仓库

执行某个阶段时，之前的所有阶段都会自动执行:
  mvn package 会先执行 validate → compile → test → package

【项目配置说明】
================================================================================

pom.xml中的重要配置:

1. <properties> - 项目属性
   - source/target: Java版本
   - encoding: 文件编码(UTF-8)
   - gson.version: Gson版本号（方便统一更新）

2. <dependencies> - 依赖声明
   - groupId: 组织ID
   - artifactId: 项目ID
   - version: 版本号
   - scope: 使用范围(compile/test/provided等)

3. <build><plugins> - 构建插件
   - maven-compiler-plugin: 编译插件
   - maven-jar-plugin: 打包插件
   - maven-javadoc-plugin: Javadoc生成
   - maven-surefire-plugin: 测试运行
   - maven-assembly-plugin: 组装插件

【使用建议】
================================================================================

✓ 推荐使用 mvn clean package -DskipTests 快速打包
✓ 定期检查依赖更新: mvn versions:display-dependency-updates
✓ 在CI/CD流程中使用Maven自动化构建
✓ 利用Maven的插件生态系统扩展功能
✓ 遵循Maven目录结构规范，提高项目可维护性

【后续步骤】
================================================================================

1. ✓ pom.xml已创建，支持Maven构建
2. ✓ 可以删除lib/目录，依赖通过Maven管理
3. ✓ 可以删除.iml配置文件，让IDEA自动生成
4. ✓ 可以将target/目录添加到.gitignore
5. → 运行 mvn clean compile 验证配置

================================================================================
                               END
================================================================================

