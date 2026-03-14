# 项目配置和使用指南

## Git 仓库结构

当前项目结构：
```
D:\Project4J\compiler/          # 根项目（Git仓库）
├── .git/                       # Git版本控制
├── .gitignore                  # Git忽略配置
├── compiler.iml                # IDEA项目配置
├── lab1/                       # Lab1子项目
│   ├── lab1.iml               # Lab1项目配置
│   ├── src/                   # 源代码
│   │   ├── Main.java
│   │   ├── main/resources/    # 资源文件夹
│   │   │   └── settings.json  # JSON配置文件
│   │   ├── model/
│   │   ├── service/
│   │   └── view/
│   ├── test/                  # 测试文件
│   ├── lib/                   # 依赖库
│   │   └── gson-2.10.1.jar   # GSON库
│   └── bin/                   # 编译输出
└── README.md
```

## 解决的问题

### 1. Git 结构问题

**问题描述**：
- 原来只有 `lab1` 文件夹下有 `.git`
- 删除后提交记录丢失
- 无法从上层统一推送到远程

**解决方案**：
- ✓ 在 `compiler` 文件夹（上层）创建 Git 仓库
- ✓ 所有代码和子项目都在一个 Git 仓库中管理
- ✓ 可以统一提交和推送到远程仓库

### 2. GSON 库配置

**问题描述**：
- GSON库未被正确加载到项目中
- IDEA显示 `Cannot resolve symbol 'google'` 错误

**解决方案**：
- ✓ 在 `lab1.iml` 中添加 GSON 库配置
- ✓ 在 `compiler.iml` 中添加 GSON 库配置
- ✓ 添加资源文件夹 (`src/main/resources`) 配置

### 3. JSON 配置加载器实现

**完成功能**：
- ✓ 实现 `JsonFileTaxConfigLoader` 类
- ✓ 支持从 classpath 加载 `settings.json` 文件
- ✓ 使用 GSON 进行 JSON 解析和反序列化
- ✓ 完整的数据验证（起征点、税率规则）
- ✓ DTO 类用于 JSON 数据结构映射

## 使用方法

### 编译项目

```bash
cd D:\Project4J\compiler\lab1

# 编译所有源文件
javac -encoding UTF-8 -cp "lib/gson-2.10.1.jar" -d bin `
  src/model/entity/*.java `
  src/model/loader/*.java `
  src/service/*.java `
  src/view/*.java `
  src/Main.java
```

### 运行项目

```bash
cd D:\Project4J\compiler\lab1

# 运行主程序
java -cp "lib/gson-2.10.1.jar;bin;src/main/resources" Main
```

### 运行测试

```bash
cd D:\Project4J\compiler\lab1

# 运行功能测试
javac -encoding UTF-8 -cp "lib/gson-2.10.1.jar;bin" -d bin test/JsonLoaderFunctionalTest.java
java -cp "lib/gson-2.10.1.jar;bin;src/main/resources" JsonLoaderFunctionalTest
```

## Git 操作

### 查看提交历史

```bash
cd D:\Project4J\compiler

# 查看最近提交
git log --oneline -5

# 查看完整提交信息
git log -1
```

### 推送到远程仓库

```bash
cd D:\Project4J\compiler

# 添加远程仓库 (如果还没有)
git remote add origin <远程仓库URL>

# 推送到主分支
git push -u origin master
```

### 提交新更改

```bash
cd D:\Project4J\compiler

# 查看状态
git status

# 添加所有更改
git add .

# 提交更改
git commit -m "提交说明"

# 推送到远程
git push
```

## JSON 配置文件格式

文件位置：`src/main/resources/settings.json`

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
      // ... 更多规则
    ]
  }
}
```

## 功能测试结果

✓ 所有测试通过

```
✓ 起征点: 1600.0 元
✓ 税率规则数量: 5 条
✓ 第一条规则: 等级=1, 最小=0.0, 最大=500.0, 税率=0.05
✓ 最后一条规则: 等级=5, 最小=20000.0, 最大=1.7976931348623157E308, 税率=0.25
✓ 所有测试通过！JSON配置加载功能正常。
```

## 项目配置要点

### IDEA 项目配置（.iml 文件）

1. **根项目配置** (`compiler.iml`)：
   - 指定源文件夹：`lab1/src`
   - 指定资源文件夹：`lab1/src/main/resources`
   - 添加 GSON 库：`lab1/lib/gson-2.10.1.jar`

2. **子项目配置** (`lab1/lab1.iml`)：
   - 指定源文件夹：`src`
   - 指定资源文件夹：`src/main/resources`
   - 添加 GSON 库：`lib/gson-2.10.1.jar`

### 编码配置

- 所有 Java 源文件使用 UTF-8 编码
- 编译时指定 `-encoding UTF-8` 参数

## 后续步骤

1. **配置远程仓库**：
   ```bash
   git remote add origin <GitHub/GitLab URL>
   git push -u origin master
   ```

2. **添加更多功能**：
   - 可以在 `src/service/` 中添加更多业务逻辑
   - 可以在 `test/` 中添加更多单元测试

3. **优化配置加载**：
   - 可以添加配置热更新功能
   - 可以支持多个配置文件
   - 可以添加配置文件验证

## 常见问题

### Q: 如何在 IDEA 中刷新项目配置？
A: 右键项目 → Reload Project 或按 Ctrl+Shift+Alt+S 打开项目设置

### Q: 如何解决 GSON 库引入问题？
A: 确保 `lib/gson-2.10.1.jar` 文件存在，并在 `.iml` 文件中正确配置

### Q: 如何添加新的税率规则？
A: 编辑 `src/main/resources/settings.json` 文件，在 `taxRulesList` 中添加新规则

### Q: 如何备份项目？
A: 使用 `git clone` 复制整个项目，所有历史记录都会被保留


