========================================
  Lab02 - Agenda Service
  议程管理系统
========================================

一、实验完成人基本信息
--------------------
姓名：林浩宇
学号：23366037
课程：Java程序设计
实验：Lab02 - Agenda Service

二、提交结果描述
----------------
本项目实现了一个完整的议程管理系统，支持以下功能：

1. 用户管理
   - register <username> <password>：注册新用户

2. 会议管理
   - add <username> <password> <other> <start> <end> <title>：创建会议
   - query <username> <password> <start> <end>：按时间范围查询会议
   - delete <username> <password> <meetingId>：删除指定会议
   - clear <username> <password>：清空用户所有会议

3. 批量执行
   - batch <filename>：从文件中读取并批量执行命令

4. 退出
   - quit：退出程序

5. 远程访问
   - 支持通过TCP Socket远程连接服务端
   - 服务端支持多客户端并发连接（thread-per-connection模型）

技术特点：
- 采用Command Pattern（命令模式）作为核心架构
- 分层设计：Domain -> Mapper -> Service -> Command -> Executor
- IOHandler抽象：统一控制台和Socket两种I/O方式
- 完整的异常体系：BizException基类 + 7个具体异常子类
- 会议时间冲突检测：防止用户创建时间重叠的会议
- 线程安全的ID生成器（AtomicInteger）

项目结构：
  lab2/
  ├── build.bat          编译脚本
  ├── agenda.bat         运行本地议程服务
  ├── clean.bat          清理编译产物
  ├── test.bat           自动化测试脚本
  ├── design.docx        面向对象设计文档（含UML类图）
  ├── test.docx          测试用例文档
  ├── readme.txt         本文件
  ├── pom.xml            Maven配置
  ├── bin/               编译输出目录
  ├── doc/               Javadoc文档目录
  ├── test/              测试用例目录
  │   ├── 01_basic_flow.txt
  │   ├── 02_error_cases.txt
  │   ├── 03_conflict_detection.txt
  │   ├── 04_full_lifecycle.txt
  │   ├── 05_batch_command.txt
  │   └── batch_input.txt
  └── src/main/java/     源代码
      ├── AgendaService.java      本地模式入口
      ├── AgendaServer.java       TCP服务端入口
      ├── AgendaClient.java       TCP客户端入口
      ├── AppContext.java         应用上下文（组件装配）
      ├── domain/                 领域模型
      │   ├── User.java
      │   └── Meeting.java
      ├── mapper/                 数据访问接口
      │   ├── UserMapper.java
      │   ├── MeetingMapper.java
      │   └── impl/               数据访问实现
      │       ├── DefaultUserMapper.java
      │       └── DefaultMeetingMapper.java
      ├── service/                业务逻辑层
      │   ├── UserService.java
      │   └── MeetingService.java
      ├── command/                命令框架
      │   ├── Command.java        命令接口
      │   ├── Response.java       响应封装
      │   ├── enums/
      │   │   └── CommandType.java
      │   ├── registry/
      │   │   └── CommandRegistry.java
      │   ├── parse/
      │   │   ├── CommandParser.java
      │   │   ├── ParsedCommand.java
      │   │   └── SimpleCommandParser.java
      │   ├── executor/
      │   │   └── CommandExecutor.java
      │   └── impl/               命令实现
      │       ├── RegisterCommand.java
      │       ├── AddMeetingCommand.java
      │       ├── QueryMeetingCommand.java
      │       ├── DeleteCommand.java
      │       ├── ClearCommand.java
      │       ├── BatchCommand.java
      │       └── QuitCommand.java
      ├── exception/              异常体系
      │   ├── BizException.java
      │   ├── UserNotFoundException.java
      │   ├── UserAlreadyExistsException.java
      │   ├── AuthFailedException.java
      │   ├── EmptyCommandException.java
      │   ├── UnknownCommandException.java
      │   ├── InvalidArgumentException.java
      │   ├── InvalidTimeFormatException.java
      │   └── MeetingConflictException.java
      ├── ui/                     I/O抽象
      │   ├── IOHandler.java
      │   ├── ConsoleIOHandler.java
      │   └── SocketIOHandler.java
      └── utils/                  工具类
          ├── IdGenerator.java
          └── TimeUtil.java

三、实验心得
----------------
通过本次实验，我对Java面向对象设计有了更深入的理解和实践：

1. 设计模式的应用
   通过实现Command Pattern，我深刻体会到了将"请求"封装为对象的
   好处。每个命令都是独立的类，遵循单一职责原则，新增命令时只需
   添加新的Command实现类并注册到Registry中，完全符合开闭原则。

2. 分层架构的价值
   Domain-Mapper-Service-Command的分层设计让各层职责清晰。
   Mapper层屏蔽了存储细节，Service层封装业务逻辑，Command层
   只负责参数解析和结果格式化。这种分层使得代码易于理解和维护。

3. 接口抽象的威力
   IOHandler接口的抽象让我可以在不修改任何业务代码的情况下，
   支持Console和Socket两种完全不同的I/O方式。这正是面向接口
   编程的魅力所在——依赖抽象而非具体实现。

4. 异常体系的设计
   统一的BizException基类让异常处理更加规范。每种业务错误都
   有对应的异常类，携带清晰的英文错误信息，方便上层统一捕获
   和展示。

5. 并发编程初探
   通过实现TCP服务端的thread-per-connection模型，我初步了解
   了Java多线程编程。AtomicInteger的使用也让我认识到了并发
   环境下数据安全的重要性。

6. 不足与展望
   当前系统使用内存存储，重启后数据丢失。未来可以考虑引入文件
   持久化或数据库。此外，thread-per-connection模型在高并发
   场景下可能不够高效，可以考虑线程池优化。
