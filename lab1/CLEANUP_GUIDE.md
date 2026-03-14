================================================================================
                    使用Maven后的项目清理方案
================================================================================

【可以删除的文件】
================================================================================

1️⃣ lib/ 文件夹 ✓ 可删除
   ├─ gson-2.10.1.jar (3.2 MB)
   └─ 原因: Gson已在pom.xml中声明，Maven会自动管理
   
   删除命令:
     Remove-Item -Recurse -Force lib/
     或在Git中: git rm -r lib/

2️⃣ lab1.iml 文件 ✓ 可删除
   └─ IDEA IDE配置文件
   └─ 原因: IDEA会根据pom.xml自动生成
   
   删除命令:
     Remove-Item lab1.iml
     或在Git中: git rm lab1.iml

3️⃣ bin/ 文件夹 ✓ 可删除
   └─ 手动编译的输出目录
   └─ 原因: Maven使用target/替代
   
   删除命令:
     Remove-Item -Recurse -Force bin/
     或在Git中: git rm -r bin/

4️⃣ out/ 文件夹 ✓ 可删除
   └─ IDE输出目录
   └─ 原因: Maven使用target/替代
   
   删除命令:
     Remove-Item -Recurse -Force out/
     或在Git中: git rm -r out/

【保留的文件】
================================================================================

必须保留:

✓ src/ - 源代码（核心）
✓ test/ - 测试文件（核心）
✓ settings.json - 配置文件（数据）
✓ pom.xml - Maven配置（必须）
✓ .gitignore - Git忽略规则（推荐）

应该保留:

✓ docs/ - javadoc文档（有价值）
✓ design.md - 设计文档（重要）
✓ readme.txt - 自述文件（重要）
✓ MAVEN_GUIDE.md - Maven使用指南（有用）
✓ MAVEN_INSTALLATION.md - 安装指南（有用）
✓ 其他说明文档 - 项目文档（参考价值）

【清理步骤】
================================================================================

方案1: Git中删除（推荐，保留删除历史）
------

1. 进入项目目录:
   cd D:\Project4J\compiler\lab1

2. 删除不需要的文件和文件夹:
   
   # 删除lib文件夹
   git rm -r lib/
   
   # 删除iml配置文件
   git rm lab1.iml
   
   # 删除编译输出
   git rm -r bin/
   git rm -r out/

3. 提交删除:
   git commit -m "删除不再需要的文件（使用Maven后）
   
   已删除文件:
   ✓ lib/ - Gson jar包（由Maven管理）
   ✓ lab1.iml - IDE配置（由IDEA自动生成）
   ✓ bin/ - 编译输出（使用target/替代）
   ✓ out/ - IDE输出（使用target/替代）"

4. 完成！

方案2: 直接删除（快速方法）
------

1. 进入项目目录:
   cd D:\Project4J\compiler\lab1

2. 删除文件:
   Remove-Item -Recurse -Force lib/
   Remove-Item lab1.iml
   Remove-Item -Recurse -Force bin/
   Remove-Item -Recurse -Force out/

3. 同步到Git:
   git add -A
   git commit -m "删除不再需要的文件"

4. 完成！

【删除后的项目结构】
================================================================================

清理前:
lab1/
├── lib/ ...................... ✗ 删除
├── bin/ ...................... ✗ 删除
├── out/ ...................... ✗ 删除
├── lab1.iml .................. ✗ 删除
├── src/
├── test/
├── pom.xml
└── ...

清理后 (更简洁):
lab1/
├── src/ ...................... ✓ 源代码
├── test/ ..................... ✓ 测试
├── settings.json ............ ✓ 配置
├── pom.xml .................. ✓ Maven配置
├── docs/ ..................... ✓ 文档
├── design.md ................. ✓ 设计文档
├── readme.txt ................ ✓ 自述文件
└── MAVEN_*.md ................ ✓ 指南文档

文件夹数量: 从5个减到3个
项目大小: 减少约3.2 MB

【验证清理结果】
================================================================================

清理后应该验证:

1. 检查项目结构:
   tree /F lab1

2. 重新导入IDEA项目:
   File → Open → 选择lab1文件夹
   IDEA会自动根据pom.xml生成新的.iml文件

3. 验证Maven构建:
   mvn clean compile
   输出应该为: BUILD SUCCESS

4. 验证IDEA识别:
   右键项目 → Maven → Reimport
   确保IDEA正确识别Maven项目

【如何恢复（如果需要）】
================================================================================

如果不小心删除了重要文件，可以从Git恢复:

# 恢复所有已删除的文件:
git checkout HEAD~1 -- lib/ bin/ out/ lab1.iml

# 或恢复单个文件:
git checkout HEAD -- lab1.iml

【Maven工作流程（清理后）】
================================================================================

新的开发流程:

1. 编辑源代码 (src/)
2. 运行: mvn compile
   - Maven自动下载Gson到本地仓库
   - 输出到target/classes/

3. 运行测试: mvn test

4. 打包发布: mvn clean package
   - 生成target/tax-calculator-1.0.0.jar

5. 在IDEA中:
   - Rebuild Project (自动调用Maven)
   - Run → 直接运行

【注意事项】
================================================================================

⚠️ 删除前检查:
  ✓ 确认没有重要数据在lib/、bin/、out/中
  ✓ 确认settings.json已在根目录
  ✓ 确认已经提交了代码到Git

✓ 删除后效果:
  ✓ 项目更简洁
  ✓ Git仓库更轻量
  ✓ Maven自动管理依赖
  ✓ IDE自动生成配置

【常见问题】
================================================================================

Q: 删除lib后，程序能运行吗?
A: 可以。Maven会在运行时自动下载Gson到C:\Users\你的用户名\.m2\repository\

Q: 删除.iml后IDEA还能用吗?
A: 可以。IDEA会根据pom.xml自动生成新的.iml文件。
   如果没有，右键项目 → Mark Directory as → Sources Root

Q: 如何确保Maven正确配置了?
A: 运行: mvn dependency:tree
   应该看到Gson在列表中

Q: target/文件夹能删除吗?
A: 可以。这是编译输出，运行 mvn clean 会删除它。
   Git会自动忽略（已在.gitignore中）

【总结】
================================================================================

清理前:
  ✓ 功能完整
  ✗ 项目较大
  ✗ 配置复杂
  ✗ 手动管理依赖

清理后:
  ✓ 功能完整
  ✓ 项目轻量
  ✓ 配置简洁
  ✓ Maven自动管理依赖

建议: 立即执行清理！

================================================================================

