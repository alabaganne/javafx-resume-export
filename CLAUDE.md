# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Run Commands

```bash
# Run the application
mvn javafx:run

# Clean and run
mvn clean javafx:run

# Compile only
mvn clean compile

# Package as JAR
mvn clean package
```

## Requirements

- Java 17+
- Maven 3.6+

## Architecture

This is a JavaFX 17 desktop application for building and exporting resumes to PDF.

### Key Components

- **Main.java** (`src/main/java/com/example/resumefx/Main.java`): Single-file UI containing all JavaFX components, form handling, and dialog management. The UI is built programmatically (no FXML) using a tab-based layout with 7 sections.

- **Model Layer** (`model/`): Plain Java objects representing resume data - Resume (container), PersonalInfo, Education, Experience, Project, Skill, Language, Certification.

- **PDFExportService** (`service/PDFExportService.java`): Generates PDF output using Apache PDFBox. Handles page management, text wrapping, and section formatting.

### Module System

The project uses Java modules. The `module-info.java` requires:
- `javafx.controls`
- `javafx.fxml`
- `java.desktop` (for AWT Color used in PDF generation)
- `org.apache.pdfbox`

### Styling

CSS styling is loaded from `/style.css` resource and applied to the main scene.
