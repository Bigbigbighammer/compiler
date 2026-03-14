================================================================================
                  Maven 安装和配置指南
================================================================================

【检查是否已安装Maven】
================================================================================

在Windows PowerShell中运行:
  mvn --version

如果出现版本信息，说明已安装。
如果命令不识别，需要进行安装。

【Windows上安装Maven】
================================================================================

方式1: 使用Chocolatey (推荐，最简便)
------
1. 打开PowerShell（以管理员身份）
2. 运行以下命令:
   
   choco install maven

3. 验证安装:
   mvn --version

方式2: 手动安装
------
1. 下载Maven
   https://maven.apache.org/download.cgi
   下载: apache-maven-3.9.x-bin.zip (适合你的版本)

2. 解压到合适的目录
   例如: C:\Program Files\Apache\maven

3. 配置环境变量
   
   a) 右键"此电脑" → 属性 → 高级系统设置 → 环境变量
   
   b) 创建新的系统变量:
      变量名: MAVEN_HOME
      变量值: C:\Program Files\Apache\maven
   
   c) 编辑PATH变量，添加:
      %MAVEN_HOME%\bin
   
   d) 重启PowerShell，验证:
      mvn --version

方式3: 使用Docker (如果已安装Docker)
------
   docker run -it maven:3.9 mvn --version

【配置Maven】
================================================================================

Maven配置文件位置:
  C:\Users\你的用户名\.m2\settings.xml

通常不需要修改，使用默认配置即可。

如需配置代理或镜像源，编辑该文件。

【检查Java版本】
================================================================================

Maven需要Java环境支持，检查Java版本:

  java -version

要求: Java 1.8 或更高版本

如果未安装Java:
  https://www.oracle.com/java/technologies/downloads/

【项目使用Maven】
================================================================================

现在项目已配置了pom.xml，可以使用Maven进行构建。

在项目根目录（包含pom.xml的目录）运行:

1. 验证配置
   mvn validate

2. 下载依赖（首次运行）
   mvn dependency:resolve

3. 编译代码
   mvn compile

4. 运行测试
   mvn test

5. 打包项目
   mvn clean package

6. 完整构建流程
   mvn clean install

【Maven第一次运行】
================================================================================

第一次运行Maven时，会下载所有依赖：

1. 进入项目目录:
   cd D:\Project4J\compiler\lab1

2. 运行编译:
   mvn compile

3. 第一次会花费较长时间（下载依赖）
   等待完成即可

4. 依赖下载位置:
   C:\Users\你的用户名\.m2\repository\

【常见问题】
================================================================================

Q1: 下载依赖太慢?
A: 配置Maven镜像源，编辑 C:\Users\你的用户名\.m2\settings.xml
   添加阿里云镜像:
   
   <mirror>
     <id>aliyunmaven</id>
     <mirrorOf>central</mirrorOf>
     <name>Aliyun Maven</name>
     <url>https://maven.aliyun.com/repository/central</url>
   </mirror>

Q2: 内存不足错误?
A: 增加Maven内存:
   set MAVEN_OPTS=-Xmx1024m
   mvn clean package

Q3: 编码错误?
A: 设置字符编码:
   set MAVEN_OPTS=-Dfile.encoding=UTF-8
   mvn compile

Q4: 无法连接到远程仓库?
A: 检查网络连接
   或配置本地仓库离线使用:
   mvn -o compile

【项目转换】
================================================================================

现在项目已支持Maven，建议:

1. ✓ pom.xml已创建
2. ✓ .gitignore已更新，包含target/等Maven目录
3. → 可以删除lib/目录中的jar文件（由Maven管理）
4. → 可以删除.iml文件（IDE会自动生成）
5. → 删除bin/目录（Maven使用target/classes）

转换后的新工作流:

编译:
  mvn clean compile

运行程序:
  java -cp target/classes:${HOME}/.m2/repository/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar Main

或生成jar后运行:
  mvn clean package
  java -jar target/tax-calculator-1.0.0.jar

【后续优化】
================================================================================

1. 配置本地私有仓库
   用于存储内部项目

2. 配置持续集成 (CI/CD)
   使用GitHub Actions运行: mvn clean verify

3. 发布到中央仓库
   发布开源项目供他人使用

4. 使用Maven插件
   - maven-checkstyle-plugin: 代码风格检查
   - maven-findbugs-plugin: 代码质量分析
   - maven-cobertura-plugin: 代码覆盖率

【验证安装成功】
================================================================================

如果以上步骤都完成，运行测试验证:

1. 进入项目目录:
   cd D:\Project4J\compiler\lab1

2. 编译项目:
   mvn clean compile

3. 如果成功输出 BUILD SUCCESS，说明Maven配置正确!

【获取帮助】
================================================================================

Maven官方文档:
  https://maven.apache.org/

Maven教程:
  https://maven.apache.org/guides/

设置代理:
  https://maven.apache.org/guides/mini/guide-proxies.html

================================================================================
                               END
================================================================================

