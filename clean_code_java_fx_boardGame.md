# Clean Code Plan - JavaFX BoardGame

## Project Overview
- **Project**: JavaFXBoardGame v0.0.1-alpha
- **Java Version**: 24
- **Build Tool**: Maven
- **Current Structure**: 4 Java files in `com.willow.javafxboardgame` package

---

## Implementation Status

| Phase | Description | Status |
|-------|-------------|--------|
| Phase 1 | Add Checkstyle Plugin to Maven | COMPLETED |
| Phase 2 | Create Custom Checkstyle Configuration | COMPLETED |
| Phase 3 | Create Suppression File | COMPLETED |
| Phase 4 | Fix Current Code Issues | COMPLETED |
| Phase 5 | Maven Commands | READY |
| Phase 6 | IDE Integration | PENDING |
| Phase 7 | Java 24 Native Access Warnings | COMPLETED |

---

## Phase 1: Add Checkstyle Plugin to Maven - COMPLETED

Added to `pom.xml`:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>3.3.1</version>
    <dependencies>
        <dependency>
            <groupId>com.puppycrawl.tools</groupId>
            <artifactId>checkstyle</artifactId>
            <version>10.18.1</version>
        </dependency>
    </dependencies>
    <configuration>
        <configLocation>checkstyle.xml</configLocation>
        <suppressionsLocation>checkstyle-suppressions.xml</suppressionsLocation>
        <consoleOutput>true</consoleOutput>
        <failsOnError>true</failsOnError>
        <linkXRef>false</linkXRef>
    </configuration>
    <executions>
        <execution>
            <id>validate</id>
            <phase>validate</phase>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

---

## Phase 2: Custom Checkstyle Configuration - COMPLETED

### Key Differences from SonarQube Default Rules

| Rule Category | Checkstyle (Custom) | SonarQube Default |
|---------------|---------------------|-------------------|
| **Line Length** | 120 chars | 200 chars (lenient) |
| **Method Length** | Max 30 lines | Max 100 lines |
| **Parameter Count** | Max 5 | Max 7 |
| **Cyclomatic Complexity** | Max 10 | Max 15 |
| **Nested If Depth** | Max 2 | Max 3 |
| **Boolean Expression** | Max 3 | Not enforced |
| **Constant Naming** | UPPER_SNAKE_CASE enforced | Suggestion only |
| **Static Variable Naming** | camelCase prefix | Not strict |
| **Import Order** | Grouped, alphabetical | Any order |
| **Magic Numbers** | Forbidden | Warning only |
| **Final Local Variables** | Encouraged | Not enforced |

**File created**: `checkstyle.xml`

---

## Phase 3: Suppression File - COMPLETED

**File created**: `checkstyle-suppressions.xml`

Current suppressions for gradual adoption:
- `module-info.java` - excluded from all checks (special syntax not supported by Checkstyle)
- `MagicNumber` - suppressed in `UIBoardGame.java` (UI coordinates/sizes)
- `MethodLength` - suppressed in `UIBoardGame.java` (UI init methods)
- `ImportOrder` - suppressed globally (can enable later)
- `MissingJavadocMethod` - suppressed globally
- `MissingJavadocType` - suppressed globally
- `HideUtilityClassConstructor` - suppressed for `UIBoardGame.java`

**Note**: `checkstyle.xml` also includes `BeforeExecutionExclusionFileFilter` to exclude `module-info.java` from processing (required to prevent parsing errors).

---

## Phase 4: Code Issues Fixed - COMPLETED

### 4.1 UIBoardGame.java - FIXED

| Issue | Original | Fixed To | Status |
|-------|----------|----------|--------|
| `Q` array naming | `Q` | `QUADRANTS` | FIXED |
| `Shader` array naming | `Shader` | `SHADERS` | FIXED |
| `q` array naming | `q` | `MAIN_BOARDS` | FIXED |
| `Q1S` naming | `Q1S` | `QUADRANT_1_SQUARES` | FIXED |
| `Q2S` naming | `Q2S` | `QUADRANT_2_SQUARES` | FIXED |
| `Q3S` naming | `Q3S` | `QUADRANT_3_SQUARES` | FIXED |
| `Q4S` naming | `Q4S` | `QUADRANT_4_SQUARES` | FIXED |
| Whitespace before `{` | `createMaterials(){` | `createMaterials() {` | FIXED |
| Line length violations | 11 lines > 120 chars | Wrapped to multiple lines | FIXED |

### 4.2 GameControllerHelper.java - FIXED

| Issue | Original | Fixed To | Status |
|-------|----------|----------|--------|
| Missing `final` class | `public class` | `public final class` | FIXED |
| Modifier order line 12 | `public final static` | `public static final` | FIXED |
| Modifier order line 23 | `public final static` | `public static final` | FIXED |
| Constant naming | `keyPressed` | `KEY_PRESSED` | FIXED |
| Constant naming | `keyReleased` | `KEY_RELEASED` | FIXED |

### 4.3 BoardGame.java - FIXED

| Issue | Original | Fixed To | Status |
|-------|----------|----------|--------|
| Double blank line (line 9-10) | Two blank lines | Single blank line | FIXED |
| Double blank line (line 20-21) | Two blank lines | Single blank line | FIXED |
| Missing newline at EOF | No newline | Added newline | FIXED |

---

## Phase 5: Maven Commands - READY

```bash
# Run Checkstyle check
mvn checkstyle:check

# Generate Checkstyle report (HTML in target/site/)
mvn checkstyle:checkstyle

# Run with full build
mvn clean verify

# Skip Checkstyle temporarily
mvn clean verify -Dcheckstyle.skip=true
```

---

## Phase 6: IDE Integration - PENDING

### 6.1 IntelliJ IDEA
1. Install "CheckStyle-IDEA" plugin
2. Settings → Tools → Checkstyle
3. Add configuration file: `checkstyle.xml`
4. Set as active configuration

### 6.2 VS Code
1. Install "Checkstyle for Java" extension
2. Set `java.checkstyle.configuration` to project's `checkstyle.xml`

---

## Phase 7: Java 24 Native Access Warnings - COMPLETED

Java 24 enforces stricter encapsulation of internal APIs, causing warnings when running JavaFX applications.

### 7.1 Problem

```
WARNING: java.lang.System::load has been called by com.sun.glass.utils.NativeLibLoader
WARNING: Use --enable-native-access=javafx.graphics to avoid a warning
```

### 7.2 Solution

| File | Change |
|------|--------|
| `pom.xml` | Added `--enable-native-access=javafx.graphics` to javafx-maven-plugin |
| `.mvn/wrapper/maven-wrapper.properties` | Updated Maven 3.8.5 → 3.9.9 |
| `.mvn/jvm.config` | Created with `--enable-native-access=ALL-UNNAMED` |

### 7.3 pom.xml Configuration

```xml
<plugin>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-maven-plugin</artifactId>
    <version>0.0.8</version>
    <executions>
        <execution>
            <id>default-cli</id>
            <configuration>
                <mainClass>com.willow.javafxboardgame/com.willow.javafxboardgame.BoardGame</mainClass>
                <!-- ... other options ... -->
                <options>
                    <option>--enable-native-access=javafx.graphics</option>
                </options>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### 7.4 .mvn/jvm.config

```
--enable-native-access=ALL-UNNAMED
```

### 7.5 Remaining Warning (Cannot Be Fixed)

The `sun.misc.Unsafe` warning from Guava cannot be suppressed:
```
WARNING: sun.misc.Unsafe::objectFieldOffset has been called by com.google.common.util.concurrent.AbstractFuture$UnsafeAtomicHelper
```

This is a known issue tracked in:
- [Guava #7565](https://github.com/google/guava/issues/7565)
- [Maven MNG-8399](https://github.com/apache/maven/issues/10228)

The warning is cosmetic and will be resolved when Guava migrates to `VarHandle`.

---

## Future Improvements (Backlog)

### Remove Suppressions Gradually
Once code is refactored, remove these suppressions from `checkstyle-suppressions.xml`:

| Suppression | Action Required |
|-------------|-----------------|
| `MagicNumber` in UIBoardGame | Extract UI constants (coordinates, sizes, colors) |
| `MethodLength` in UIBoardGame | Split large methods into smaller ones |
| `ImportOrder` globally | Reorganize imports in all files |
| `MissingJavadoc*` | Add Javadoc to public APIs |

### Extract Magic Numbers
Create named constants for UI values currently suppressed by MagicNumber rule:
```java
// Example constants to extract from UIBoardGame
private static final int SCENE_WIDTH = 1280;
private static final int SCENE_HEIGHT = 640;
private static final int BOARD_SIZE = 300;
private static final int BOARD_DEPTH = 5;
private static final int SUB_BOARD_SIZE = 150;
private static final double DROP_SHADOW_RADIUS = 0.3;
private static final int DROP_SHADOW_OFFSET = 3;
// ... and other numeric literals
```

### Refactor Long Methods
Methods to consider splitting in `UIBoardGame.java`:
- `createBoardGameNodes()` - ~50 lines, split by component type
- `loadImageAssets()` - ~25 lines, group by asset type
- `createTextAssets()` - ~55 lines, extract individual text creation

---

## Summary: Checkstyle vs SonarQube Comparison

| Aspect | Checkstyle (This Config) | SonarQube Default |
|--------|--------------------------|-------------------|
| **Focus** | Code style & conventions | Bugs, security, smells |
| **Strictness** | High | Medium |
| **Line length** | 120 | 200 |
| **Method length** | 30 | 100 |
| **Complexity** | 10 | 15 |
| **Magic numbers** | Error | Warning |
| **Import order** | Enforced | Not enforced |
| **Naming** | Strict patterns | Flexible |
| **Braces** | Always required | Suggested |
| **Execution** | Build-time | Server analysis |

---

## Files Modified/Created

| File | Action |
|------|--------|
| `pom.xml` | Modified - checkstyle plugin, JavaFX native access option |
| `checkstyle.xml` | Created - custom configuration with `BeforeExecutionExclusionFileFilter` for module-info.java |
| `checkstyle-suppressions.xml` | Created - suppression rules including module-info.java exclusion |
| `UIBoardGame.java` | Modified - UPPER_SNAKE_CASE constants, line length fixes |
| `GameControllerHelper.java` | Modified - final class, modifier order, UPPER_SNAKE_CASE constants |
| `BoardGame.java` | Modified - blank lines, newline at EOF |
| `.mvn/wrapper/maven-wrapper.properties` | Modified - Maven 3.8.5 → 3.9.9 |
| `.mvn/jvm.config` | Created - native access flag for Maven |
