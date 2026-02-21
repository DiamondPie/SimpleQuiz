<div align="center">
  <h1>SimpleQuiz</h1>
  <p>A lightweight, high-performance automated quiz plugin designed for modern Minecraft servers (Paper 1.21.1+)</p>

**English** | [简体中文](README.zh-CN.md)

</div>

---

## 🌟 Introduction

**SimpleQuiz** is designed to increase server activity by providing automated math problems and customizable fill-in-the-blank questions, creating an engaging and interactive experience for players.

The plugin uses a dynamic timing mechanism based on server TPS calculations to ensure accurate operation even under high server load and low TPS conditions.

---

## ✨ Core Features

**Dual-Mode Question System**:

* **Math Mode**: Supports addition, subtraction, and multiplication. Includes built-in `easy-multiplier` logic to prevent overly difficult multiplication problems.
* **Fill-in-the-Blank Mode**: Supports standard text matching and a **True/False mode** (automatically appends `Yes/No` prompts).

**TPS-Based Timing**:
Dynamically calculates the number of game ticks to wait based on the server's average TPS, minimizing countdown inaccuracies caused by TPS fluctuations.

**Modern Item Support**:
Uses Paper 1.20.6+ native binary serialization (Base64), fully supporting custom reward items including enchantments, Data Components, and NBT attributes.

**Ban System**:
Robust UUID-based persistent ban logic, supporting targeted blocking of both online and offline players.

**Feedback System**:
Supports customizable BossBar countdown displays and sound effect notifications.

---

## 🛠 Build & Development

### Requirements

* **Java**: 21
* **Platform**: Paper API 1.21.1-R0.1-SNAPSHOT
* **Build Tool**: Gradle 8.x

### Gradle Build Process

1. **Clone the repository**:

```bash
git clone https://github.com/DiamondPie/SimpleQuiz.git
```

2. **Build locally**:

```bash
./gradlew clean build
```

The generated JAR file will be located in the `build/libs/` directory.

---

## 📦 Developer Integration (Maven)

This project automatically generates and publishes `pom-default.xml` via GitHub Actions. Maven users can integrate it as a dependency using the following steps.

### 1. Obtain the POM File

Download the corresponding version of `pom-default.xml` from the GitHub Release page.
This file contains all necessary metadata required to build SimpleQuiz and resolve its dependencies.

### 2. Add as a Dependency

If you have installed the project into your local Maven repository, or are referencing the published artifact, add the following to your `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>com.diamondpie</groupId>
        <artifactId>simplequiz</artifactId>
        <version>VERSION</version>
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

## 🚀 Continuous Integration (GitHub Actions)

This project includes an automated release workflow.
When a tag starting with `v` (e.g., `v1.0`) is pushed, the workflow automatically:

1. Initializes the environment using **Temurin JDK 21**
2. Builds the project and generates a production-ready JAR
3. Extracts `CHANGELOG.md` as the Release description
4. Uploads both the **JAR file** and the **Maven POM** to the GitHub Release assets for easy developer integration

---

## 📜 Commands

| Command                              | Permission         | Description                                                       |
| ------------------------------------ | ------------------ | ----------------------------------------------------------------- |
| `/simplequiz reload`                 | `simplequiz.admin` | Reloads configuration and validates reward item integrity         |
| `/simplequiz start [type] [seconds]` | `simplequiz.admin` | Forces a quiz round to start, optionally overriding the duration  |
| `/simplequiz ban <player>`           | `simplequiz.admin` | Adds a player to the UUID blacklist and broadcasts it server-wide |
| `/simplequiz unban <player>`         | `simplequiz.admin` | Removes a player from the blacklist (supports offline names)      |
| `/simplequiz encodehand`             | `simplequiz.admin` | Serializes the item in hand into a Base64 string                  |

---

## 🔧 Configuration

The generated `config.yml` file includes detailed comments for reference and customization.
