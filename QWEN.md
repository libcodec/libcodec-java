# Qwen Code Context for `libcodec-java`

## Project Overview

This is a Java-based multi-module Maven project named `libcodec`. It provides a framework for encoding and decoding data in various formats. The project is structured with a core module that defines the fundamental interfaces and base classes, and several format-specific modules (JSON, CSV, JSONB) that implement these interfaces for their respective data formats.

### Technologies

- **Language:** Java (Targeting Java 24)
- **Build Tool:** Apache Maven
- **Testing Framework:** JUnit Jupiter
- **Code Quality:** Maven Checkstyle Plugin

### Architecture

- **Parent POM:** The root `pom.xml` defines the overall project structure, shared properties, and common build plugins.
- **Core Module (`core`):** Contains the core interfaces (`Codec`, `Generator`, `Parser`) and exceptions (`CodecException`). This is the foundational module depended upon by all format-specific modules.
- **Format Modules:**
  - `json`: Implementation for JSON encoding/decoding.
  - `csv`: Implementation for CSV encoding/decoding.
  - `jsonb`: Implementation for JSON-Binding encoding/decoding.

## Building and Running

The project uses Maven for building and managing dependencies.

### Prerequisites

- Java 24 JDK
- Apache Maven

### Commands

- **Clean Build:** `mvn clean install`
  - This command cleans the project, compiles the source code, runs tests, and packages the artifacts. It's the standard command to build the entire project.
- **Compile:** `mvn compile`
  - Compiles the source code.
- **Test:** `mvn test`
  - Runs the unit tests.
- **Checkstyle:** `mvn checkstyle:check`
  - Runs the Checkstyle plugin to enforce coding standards. This is configured to run automatically during the `validate` phase of `mvn clean install`.

## Development Conventions

- **Code Style:** The project uses Checkstyle to enforce coding standards. The configuration is located at `src/checkstyle/libcodec-checks.xml`.
- **Java Version:** The project is configured to use Java 24 for both source and target compatibility.
- **Module Structure:** Each format-specific implementation should reside in its own Maven module and depend on the `core` module.
- **Testing:** Unit tests are written using JUnit Jupiter and should be placed in the `src/test/java` directory within each module.