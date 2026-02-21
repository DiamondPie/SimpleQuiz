<div align="center">
  <h1>SimpleQUiz</h1>
  <p>一个为现代 Minecraft 服务器（Paper 1.21.1+）设计的轻量级、高性能自动化问答插件</p>

[English](README.md) | **简体中文**

</div>

---

## 🌟 插件介绍

**SimpleQuiz** 旨在增加服务器活跃度，通过自动化的数学计算题和自定义填空题，为玩家提供有趣的互动体验。插件采用了动态基于tps计算计时的方案，确保在服务器高负载低tps环境下依然尽可能精准运行

## ✨ 核心特性

**双模式题目系统**：

* **数学题**：支持加减乘三则运算，内置 `easy-multiplier` 逻辑，确保乘法题不会出现过于困难的数字
* **填空题**：支持普通文本匹配及**判断题**模式（自动追加 `是/否` 提示）

**计时**：基于服务器平均tps动态计算等待的游戏刻数量，最大程度解决服务器 TPS 波动导致的倒计时延迟问题

**现代物品支持**：使用 Paper 1.20.6+ 原生二进制序列化（Base64），完美支持自定义奖品物品附魔、Data Components 和 NBT 属性

**封禁系统**：完善的 UUID 持久化封禁逻辑，支持在线/离线玩家定点屏蔽

**反馈**：支持自定义 BossBar 倒计时以及音效提示

## 🛠 构建与开发

### 环境要求

* **Java**: 21
* **平台**: Paper API 1.21.1-R0.1-SNAPSHOT
* **构建工具**: Gradle 8.x

### Gradle 构建流程

1. **克隆仓库**:
```bash
git clone https://github.com/DiamondPie/SimpleQuiz.git

```

2. **本地构建**:
```bash
./gradlew clean build

```

生成的 JAR 文件位于 `build/libs/` 目录下

---

## 📦 开发者集成 (Maven)

本项目在 GitHub Actions 中自动生成并发布 `pom-default.xml`，Maven 用户可以通过以下方式将其作为依赖引入

### 1. 获取 POM 文件

您可以从 GitHub Release 页面下载对应版本的 `pom-default.xml`。该文件包含了构建 SimpleQuiz 及其依赖所需的全部元数据

### 2. 引入依赖

如果您已将项目安装至本地 Maven 仓库，或引用了发布的 Artifact，请在您的 `pom.xml` 中添加：

```xml
<dependencies>
    <dependency>
        <groupId>com.diamondpie</groupId>
        <artifactId>simplequiz</artifactId>
        <version>版本号</version>
        <scope>provided</scope>
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
    <repository>
        <id>papermc</id>
        <url>https://repo.papermc.io/repository/maven-public/</url>
    </repository>
</repositories>

```

---

## 🚀 持续集成 (GitHub Actions)

本项目配置了自动化发布工作流。当推送以 `v` 开头的标签（如 `v1.0`）时，Action 会自动执行工作流：

1. 使用 **Temurin JDK 21** 进行环境初始化
2. 编译项目并生成生产环境 JAR
3. 自动提取 `CHANGELOG.md` 作为 Release 说明
4. 将 **JAR 文件** 与 **Maven POM** 同时上传至 Release 附件，方便开发者直接引用

---

## 📜 常用命令

| 命令 | 权限 | 说明                   |
| --- | --- |----------------------|
| `/simplequiz reload` | `simplequiz.admin` | 重载配置文件并校验奖励物品合法性     |
| `/simplequiz start [类型] [秒]` | `simplequiz.admin` | 强制开启一轮问答，支持临时覆盖时长    |
| `/simplequiz ban <玩家>` | `simplequiz.admin` | 将作弊者加入 UUID 黑名单并全服通告 |
| `/simplequiz unban <玩家>` | `simplequiz.admin` | 从黑名单移除玩家，支持离线名       |
| `/simplequiz encodehand` | `simplequiz.admin` | 将手中物品序列化为 Base64 字符串 |

## 🔧 配置

默认生成的配置文件 `config.yml` 中包含了详细的注释供您参考
