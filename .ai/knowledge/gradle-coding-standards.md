# Gradle Standards

This project uses a modular Gradle setup designed to keep module-level configuration minimal and reusable.

## Root Configuration
The root `build.gradle` is responsible for defining the plugin versions used across the project (e.g., Spring Boot, Spotless, Lombok, Jib). Plugins are generally declared here with `apply false` to manage versions centrally without forcing them on all modules.

## The `gradle/` Folder
Instead of duplicating build logic in every module, the project uses common Gradle script files located in the `gradle/` directory. These define standard conventions and are imported by the individual modules based on their type.

Key files include:
* **`java-library.gradle`**: Applied to standard Java library modules. It configures the necessary plugins, Java versions, and common tasks for building a library.
* **`java-application.gradle`**: Applied to executable Java application modules (e.g., Spring Boot apps). It includes everything from the library script plus application-specific tasks (like building an executable JAR or Docker image).
* **`repositories.gradle`**: Centralizes repository declarations (e.g., Maven Central) so they don't have to be repeated.
* **`publish.gradle`**: Contains the logic and configuration for publishing artifacts.

## Per-Module `build.gradle`
Individual modules should have extremely simple `build.gradle` files. 

1. **Import Common Configuration**: The first line should always import the appropriate base configuration from the `gradle/` directory.
   ```groovy
   // IMPORT JAVA LIBRARY CONFIG
   apply from: "$rootDir/gradle/java-library.gradle"
   ```
   *or*
   ```groovy
   // IMPORT JAVA APPLICATION CONFIG
   apply from: "$rootDir/gradle/java-application.gradle"
   ```

2. **Dependencies**: The remainder of the file should strictly be a `dependencies { ... }` block defining the specific project and external dependencies required for that module. All other configuration (like repositories, java toolchains, or testing frameworks) is handled by the imported common scripts.
   ```groovy
   dependencies {
       implementation project(":another-module")
       implementation "org.springframework:spring-context"
       
       testImplementation "org.junit.jupiter:junit-jupiter"
   }
   ```
