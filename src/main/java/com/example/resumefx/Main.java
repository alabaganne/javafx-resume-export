package com.example.resumefx;

import com.example.resumefx.model.*;
import com.example.resumefx.service.PDFExportService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main extends Application {

    private Resume resume;

    // Personal Info fields
    private TextField fullNameField, emailField, phoneField, addressField, cityField,
                     countryField, postalCodeField, linkedInField, githubField, websiteField;
    private DatePicker birthDatePicker;
    private TextArea summaryArea;

    // Lists for managing entries
    private ListView<Education> educationListView;
    private ListView<Experience> experienceListView;
    private ListView<Project> projectListView;
    private ListView<Skill> skillListView;
    private ListView<Language> languageListView;
    private ListView<Certification> certificationListView;

    @Override
    public void start(Stage stage) {
        resume = new Resume();

        // Create main container
        VBox mainContainer = new VBox();
        mainContainer.getStyleClass().add("main-container");

        // Create header
        VBox header = createHeader();

        // Create tab pane with all sections
        TabPane tabPane = createTabPane();

        // Create action buttons
        HBox actionButtons = createActionButtons(stage);
        actionButtons.setPadding(new Insets(20));
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setSpacing(15);
        actionButtons.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #e2e8f0; -fx-border-width: 1 0 0 0;");

        // Add all to main container
        mainContainer.getChildren().addAll(header, tabPane, actionButtons);
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        // Create scene and apply CSS
        Scene scene = new Scene(mainContainer, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setTitle("Resume Builder");
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.show();
    }

    private VBox createHeader() {
        VBox header = new VBox(5);
        header.getStyleClass().add("header");

        Label titleLabel = new Label("Resume Builder");
        titleLabel.getStyleClass().add("header-title");

        Label subtitleLabel = new Label("Build your professional resume with ease");
        subtitleLabel.getStyleClass().add("header-subtitle");

        header.getChildren().addAll(titleLabel, subtitleLabel);
        return header;
    }

    private TabPane createTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Create tabs
        Tab personalInfoTab = new Tab("Personal Info", createPersonalInfoPane());
        Tab educationTab = new Tab("Education", createEducationPane());
        Tab experienceTab = new Tab("Experience", createExperiencePane());
        Tab projectsTab = new Tab("Projects", createProjectsPane());
        Tab skillsTab = new Tab("Skills", createSkillsPane());
        Tab languagesTab = new Tab("Languages", createLanguagesPane());
        Tab certificationsTab = new Tab("Certifications", createCertificationsPane());

        tabPane.getTabs().addAll(personalInfoTab, educationTab, experienceTab,
                                 projectsTab, skillsTab, languagesTab, certificationsTab);

        return tabPane;
    }

    private ScrollPane createPersonalInfoPane() {
        VBox content = new VBox(20);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(30));

        // Basic Information Section
        VBox basicInfoSection = new VBox(15);
        basicInfoSection.getStyleClass().add("form-section");

        Label sectionTitle = new Label("Basic Information");
        sectionTitle.getStyleClass().add("section-title");

        GridPane basicGrid = new GridPane();
        basicGrid.getStyleClass().add("form-grid");
        basicGrid.setHgap(15);
        basicGrid.setVgap(15);

        // Row 0
        fullNameField = new TextField();
        fullNameField.setPromptText("John Doe");
        basicGrid.add(createFormField("Full Name *", fullNameField), 0, 0);

        emailField = new TextField();
        emailField.setPromptText("john.doe@email.com");
        basicGrid.add(createFormField("Email *", emailField), 1, 0);

        // Row 1
        phoneField = new TextField();
        phoneField.setPromptText("+1 234 567 8900");
        basicGrid.add(createFormField("Phone", phoneField), 0, 1);

        birthDatePicker = new DatePicker();
        birthDatePicker.setPromptText("Select date");
        basicGrid.add(createFormField("Birth Date", birthDatePicker), 1, 1);

        // Row 2
        addressField = new TextField();
        addressField.setPromptText("123 Main Street");
        basicGrid.add(createFormField("Address", addressField), 0, 2, 2, 1);

        // Row 3
        cityField = new TextField();
        cityField.setPromptText("New York");
        basicGrid.add(createFormField("City", cityField), 0, 3);

        countryField = new TextField();
        countryField.setPromptText("United States");
        basicGrid.add(createFormField("Country", countryField), 1, 3);

        // Row 4
        postalCodeField = new TextField();
        postalCodeField.setPromptText("10001");
        basicGrid.add(createFormField("Postal Code", postalCodeField), 0, 4);

        basicInfoSection.getChildren().addAll(sectionTitle, basicGrid);

        // Links Section
        VBox linksSection = new VBox(15);
        linksSection.getStyleClass().add("form-section");

        Label linksTitle = new Label("Professional Links");
        linksTitle.getStyleClass().add("section-title");

        GridPane linksGrid = new GridPane();
        linksGrid.getStyleClass().add("form-grid");
        linksGrid.setHgap(15);
        linksGrid.setVgap(15);

        linkedInField = new TextField();
        linkedInField.setPromptText("linkedin.com/in/johndoe");
        linksGrid.add(createFormField("LinkedIn", linkedInField), 0, 0);

        githubField = new TextField();
        githubField.setPromptText("github.com/johndoe");
        linksGrid.add(createFormField("GitHub", githubField), 1, 0);

        websiteField = new TextField();
        websiteField.setPromptText("www.johndoe.com");
        linksGrid.add(createFormField("Personal Website", websiteField), 0, 1, 2, 1);

        linksSection.getChildren().addAll(linksTitle, linksGrid);

        // Summary Section
        VBox summarySection = new VBox(15);
        summarySection.getStyleClass().add("form-section");

        Label summaryTitle = new Label("Professional Summary");
        summaryTitle.getStyleClass().add("section-title");

        summaryArea = new TextArea();
        summaryArea.setPromptText("Write a brief professional summary about yourself (2-3 sentences)...");
        summaryArea.setPrefRowCount(4);
        summaryArea.setWrapText(true);

        summarySection.getChildren().addAll(summaryTitle, summaryArea);

        content.getChildren().addAll(basicInfoSection, linksSection, summarySection);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        return scrollPane;
    }

    private ScrollPane createEducationPane() {
        VBox content = new VBox(20);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(30));

        educationListView = new ListView<>();
        educationListView.setPrefHeight(300);
        educationListView.setPlaceholder(new Label("No education entries yet. Click 'Add Education' to start."));

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_LEFT);

        Button addButton = new Button("Add Education");
        addButton.getStyleClass().add("button");
        addButton.setOnAction(e -> showEducationDialog(null));

        Button editButton = new Button("Edit Selected");
        editButton.getStyleClass().addAll("secondary-button", "button");
        editButton.setOnAction(e -> {
            Education selected = educationListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showEducationDialog(selected);
            }
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.getStyleClass().addAll("danger-button", "small-button", "button");
        deleteButton.setOnAction(e -> {
            Education selected = educationListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                resume.getEducationList().remove(selected);
                educationListView.getItems().remove(selected);
            }
        });

        buttonBar.getChildren().addAll(addButton, editButton, deleteButton);
        content.getChildren().addAll(educationListView, buttonBar);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private ScrollPane createExperiencePane() {
        VBox content = new VBox(20);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(30));

        experienceListView = new ListView<>();
        experienceListView.setPrefHeight(300);
        experienceListView.setPlaceholder(new Label("No work experience entries yet. Click 'Add Experience' to start."));

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_LEFT);

        Button addButton = new Button("Add Experience");
        addButton.getStyleClass().add("button");
        addButton.setOnAction(e -> showExperienceDialog(null));

        Button editButton = new Button("Edit Selected");
        editButton.getStyleClass().addAll("secondary-button", "button");
        editButton.setOnAction(e -> {
            Experience selected = experienceListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showExperienceDialog(selected);
            }
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.getStyleClass().addAll("danger-button", "small-button", "button");
        deleteButton.setOnAction(e -> {
            Experience selected = experienceListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                resume.getExperienceList().remove(selected);
                experienceListView.getItems().remove(selected);
            }
        });

        buttonBar.getChildren().addAll(addButton, editButton, deleteButton);
        content.getChildren().addAll(experienceListView, buttonBar);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private ScrollPane createProjectsPane() {
        VBox content = new VBox(20);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(30));

        projectListView = new ListView<>();
        projectListView.setPrefHeight(300);
        projectListView.setPlaceholder(new Label("No projects yet. Click 'Add Project' to start."));

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_LEFT);

        Button addButton = new Button("Add Project");
        addButton.getStyleClass().add("button");
        addButton.setOnAction(e -> showProjectDialog(null));

        Button editButton = new Button("Edit Selected");
        editButton.getStyleClass().addAll("secondary-button", "button");
        editButton.setOnAction(e -> {
            Project selected = projectListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showProjectDialog(selected);
            }
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.getStyleClass().addAll("danger-button", "small-button", "button");
        deleteButton.setOnAction(e -> {
            Project selected = projectListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                resume.getProjectList().remove(selected);
                projectListView.getItems().remove(selected);
            }
        });

        buttonBar.getChildren().addAll(addButton, editButton, deleteButton);
        content.getChildren().addAll(projectListView, buttonBar);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private ScrollPane createSkillsPane() {
        VBox content = new VBox(20);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(30));

        skillListView = new ListView<>();
        skillListView.setPrefHeight(300);
        skillListView.setPlaceholder(new Label("No skills yet. Click 'Add Skill' to start."));

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_LEFT);

        Button addButton = new Button("Add Skill");
        addButton.getStyleClass().add("button");
        addButton.setOnAction(e -> showSkillDialog(null));

        Button editButton = new Button("Edit Selected");
        editButton.getStyleClass().addAll("secondary-button", "button");
        editButton.setOnAction(e -> {
            Skill selected = skillListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showSkillDialog(selected);
            }
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.getStyleClass().addAll("danger-button", "small-button", "button");
        deleteButton.setOnAction(e -> {
            Skill selected = skillListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                resume.getSkillList().remove(selected);
                skillListView.getItems().remove(selected);
            }
        });

        buttonBar.getChildren().addAll(addButton, editButton, deleteButton);
        content.getChildren().addAll(skillListView, buttonBar);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private ScrollPane createLanguagesPane() {
        VBox content = new VBox(20);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(30));

        languageListView = new ListView<>();
        languageListView.setPrefHeight(300);
        languageListView.setPlaceholder(new Label("No languages yet. Click 'Add Language' to start."));

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_LEFT);

        Button addButton = new Button("Add Language");
        addButton.getStyleClass().add("button");
        addButton.setOnAction(e -> showLanguageDialog(null));

        Button editButton = new Button("Edit Selected");
        editButton.getStyleClass().addAll("secondary-button", "button");
        editButton.setOnAction(e -> {
            Language selected = languageListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showLanguageDialog(selected);
            }
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.getStyleClass().addAll("danger-button", "small-button", "button");
        deleteButton.setOnAction(e -> {
            Language selected = languageListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                resume.getLanguageList().remove(selected);
                languageListView.getItems().remove(selected);
            }
        });

        buttonBar.getChildren().addAll(addButton, editButton, deleteButton);
        content.getChildren().addAll(languageListView, buttonBar);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private ScrollPane createCertificationsPane() {
        VBox content = new VBox(20);
        content.getStyleClass().add("content-area");
        content.setPadding(new Insets(30));

        certificationListView = new ListView<>();
        certificationListView.setPrefHeight(300);
        certificationListView.setPlaceholder(new Label("No certifications yet. Click 'Add Certification' to start."));

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_LEFT);

        Button addButton = new Button("Add Certification");
        addButton.getStyleClass().add("button");
        addButton.setOnAction(e -> showCertificationDialog(null));

        Button editButton = new Button("Edit Selected");
        editButton.getStyleClass().addAll("secondary-button", "button");
        editButton.setOnAction(e -> {
            Certification selected = certificationListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showCertificationDialog(selected);
            }
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.getStyleClass().addAll("danger-button", "small-button", "button");
        deleteButton.setOnAction(e -> {
            Certification selected = certificationListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                resume.getCertificationList().remove(selected);
                certificationListView.getItems().remove(selected);
            }
        });

        buttonBar.getChildren().addAll(addButton, editButton, deleteButton);
        content.getChildren().addAll(certificationListView, buttonBar);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private VBox createFormField(String labelText, Control field) {
        return createFormField(labelText, field, 1, 1);
    }

    private VBox createFormField(String labelText, Control field, int colspan, int rowspan) {
        VBox fieldContainer = new VBox(5);
        Label label = new Label(labelText);
        label.getStyleClass().add("field-label");

        if (field instanceof TextField) {
            ((TextField) field).setPrefHeight(40);
        } else if (field instanceof DatePicker) {
            ((DatePicker) field).setPrefHeight(40);
        }

        fieldContainer.getChildren().addAll(label, field);

        if (colspan > 1 || rowspan > 1) {
            GridPane.setColumnSpan(fieldContainer, colspan);
            GridPane.setRowSpan(fieldContainer, rowspan);
        }

        return fieldContainer;
    }

    private HBox createActionButtons(Stage stage) {
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button exportPDFButton = new Button("Export as PDF");
        exportPDFButton.getStyleClass().addAll("success-button", "button");
        exportPDFButton.setPrefWidth(150);
        exportPDFButton.setOnAction(e -> exportToPDF(stage));

        Button clearAllButton = new Button("Clear All");
        clearAllButton.getStyleClass().addAll("secondary-button", "button");
        clearAllButton.setPrefWidth(150);
        clearAllButton.setOnAction(e -> clearAllData());

        Button exitButton = new Button("Exit");
        exitButton.getStyleClass().addAll("secondary-button", "button");
        exitButton.setPrefWidth(150);
        exitButton.setOnAction(e -> Platform.exit());

        buttonBox.getChildren().addAll(exportPDFButton, clearAllButton, exitButton);
        return buttonBox;
    }

    // Dialog methods for adding/editing entries

    private void showEducationDialog(Education education) {
        Dialog<Education> dialog = new Dialog<>();
        dialog.setTitle(education == null ? "Add Education" : "Edit Education");
        dialog.setHeaderText(education == null ? "Enter education details" : "Update education details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField degreeField = new TextField();
        degreeField.setPromptText("Bachelor of Science");
        TextField institutionField = new TextField();
        institutionField.setPromptText("University Name");
        TextField fieldOfStudyField = new TextField();
        fieldOfStudyField.setPromptText("Computer Science");
        TextField startDateField = new TextField();
        startDateField.setPromptText("Sept 2018");
        TextField endDateField = new TextField();
        endDateField.setPromptText("May 2022");
        TextField gradeField = new TextField();
        gradeField.setPromptText("3.8 GPA");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Relevant coursework, achievements...");
        descriptionArea.setPrefRowCount(3);
        CheckBox currentCheckBox = new CheckBox("Currently studying here");

        if (education != null) {
            degreeField.setText(education.getDegree());
            institutionField.setText(education.getInstitution());
            fieldOfStudyField.setText(education.getFieldOfStudy());
            startDateField.setText(education.getStartDate());
            endDateField.setText(education.getEndDate());
            gradeField.setText(education.getGrade());
            descriptionArea.setText(education.getDescription());
            currentCheckBox.setSelected(education.isCurrent());
        }

        currentCheckBox.setOnAction(e -> endDateField.setDisable(currentCheckBox.isSelected()));

        grid.add(new Label("Degree:"), 0, 0);
        grid.add(degreeField, 1, 0);
        grid.add(new Label("Institution:"), 0, 1);
        grid.add(institutionField, 1, 1);
        grid.add(new Label("Field of Study:"), 0, 2);
        grid.add(fieldOfStudyField, 1, 2);
        grid.add(new Label("Start Date:"), 0, 3);
        grid.add(startDateField, 1, 3);
        grid.add(new Label("End Date:"), 0, 4);
        grid.add(endDateField, 1, 4);
        grid.add(currentCheckBox, 1, 5);
        grid.add(new Label("Grade:"), 0, 6);
        grid.add(gradeField, 1, 6);
        grid.add(new Label("Description:"), 0, 7);
        grid.add(descriptionArea, 1, 7);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                Education edu = education != null ? education : new Education();
                edu.setDegree(degreeField.getText());
                edu.setInstitution(institutionField.getText());
                edu.setFieldOfStudy(fieldOfStudyField.getText());
                edu.setStartDate(startDateField.getText());
                edu.setEndDate(endDateField.getText());
                edu.setGrade(gradeField.getText());
                edu.setDescription(descriptionArea.getText());
                edu.setCurrent(currentCheckBox.isSelected());
                return edu;
            }
            return null;
        });

        Optional<Education> result = dialog.showAndWait();
        result.ifPresent(edu -> {
            if (education == null) {
                resume.getEducationList().add(edu);
                educationListView.getItems().add(edu);
            } else {
                educationListView.refresh();
            }
        });
    }

    private void showExperienceDialog(Experience experience) {
        Dialog<Experience> dialog = new Dialog<>();
        dialog.setTitle(experience == null ? "Add Experience" : "Edit Experience");
        dialog.setHeaderText(experience == null ? "Enter work experience details" : "Update work experience details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField jobTitleField = new TextField();
        jobTitleField.setPromptText("Software Engineer");
        TextField companyField = new TextField();
        companyField.setPromptText("Company Name");
        TextField locationField = new TextField();
        locationField.setPromptText("New York, NY");
        TextField startDateField = new TextField();
        startDateField.setPromptText("Jan 2020");
        TextField endDateField = new TextField();
        endDateField.setPromptText("Present");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Key responsibilities and achievements...");
        descriptionArea.setPrefRowCount(4);
        CheckBox currentCheckBox = new CheckBox("I currently work here");

        if (experience != null) {
            jobTitleField.setText(experience.getJobTitle());
            companyField.setText(experience.getCompany());
            locationField.setText(experience.getLocation());
            startDateField.setText(experience.getStartDate());
            endDateField.setText(experience.getEndDate());
            descriptionArea.setText(experience.getDescription());
            currentCheckBox.setSelected(experience.isCurrent());
        }

        currentCheckBox.setOnAction(e -> endDateField.setDisable(currentCheckBox.isSelected()));

        grid.add(new Label("Job Title:"), 0, 0);
        grid.add(jobTitleField, 1, 0);
        grid.add(new Label("Company:"), 0, 1);
        grid.add(companyField, 1, 1);
        grid.add(new Label("Location:"), 0, 2);
        grid.add(locationField, 1, 2);
        grid.add(new Label("Start Date:"), 0, 3);
        grid.add(startDateField, 1, 3);
        grid.add(new Label("End Date:"), 0, 4);
        grid.add(endDateField, 1, 4);
        grid.add(currentCheckBox, 1, 5);
        grid.add(new Label("Description:"), 0, 6);
        grid.add(descriptionArea, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                Experience exp = experience != null ? experience : new Experience();
                exp.setJobTitle(jobTitleField.getText());
                exp.setCompany(companyField.getText());
                exp.setLocation(locationField.getText());
                exp.setStartDate(startDateField.getText());
                exp.setEndDate(endDateField.getText());
                exp.setDescription(descriptionArea.getText());
                exp.setCurrent(currentCheckBox.isSelected());
                return exp;
            }
            return null;
        });

        Optional<Experience> result = dialog.showAndWait();
        result.ifPresent(exp -> {
            if (experience == null) {
                resume.getExperienceList().add(exp);
                experienceListView.getItems().add(exp);
            } else {
                experienceListView.refresh();
            }
        });
    }

    private void showProjectDialog(Project project) {
        Dialog<Project> dialog = new Dialog<>();
        dialog.setTitle(project == null ? "Add Project" : "Edit Project");
        dialog.setHeaderText(project == null ? "Enter project details" : "Update project details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Project Name");
        TextField roleField = new TextField();
        roleField.setPromptText("Your Role");
        TextField technologiesField = new TextField();
        technologiesField.setPromptText("Java, Spring Boot, React");
        TextField urlField = new TextField();
        urlField.setPromptText("https://github.com/...");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Project description and your contributions...");
        descriptionArea.setPrefRowCount(4);

        if (project != null) {
            nameField.setText(project.getName());
            roleField.setText(project.getRole());
            technologiesField.setText(project.getTechnologies());
            urlField.setText(project.getUrl());
            descriptionArea.setText(project.getDescription());
        }

        grid.add(new Label("Project Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Your Role:"), 0, 1);
        grid.add(roleField, 1, 1);
        grid.add(new Label("Technologies:"), 0, 2);
        grid.add(technologiesField, 1, 2);
        grid.add(new Label("URL:"), 0, 3);
        grid.add(urlField, 1, 3);
        grid.add(new Label("Description:"), 0, 4);
        grid.add(descriptionArea, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                Project proj = project != null ? project : new Project();
                proj.setName(nameField.getText());
                proj.setRole(roleField.getText());
                proj.setTechnologies(technologiesField.getText());
                proj.setUrl(urlField.getText());
                proj.setDescription(descriptionArea.getText());
                return proj;
            }
            return null;
        });

        Optional<Project> result = dialog.showAndWait();
        result.ifPresent(proj -> {
            if (project == null) {
                resume.getProjectList().add(proj);
                projectListView.getItems().add(proj);
            } else {
                projectListView.refresh();
            }
        });
    }

    private void showSkillDialog(Skill skill) {
        Dialog<Skill> dialog = new Dialog<>();
        dialog.setTitle(skill == null ? "Add Skill" : "Edit Skill");
        dialog.setHeaderText(skill == null ? "Enter skill details" : "Update skill details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Skill name (e.g., Java, Python)");
        ComboBox<String> levelCombo = new ComboBox<>();
        levelCombo.getItems().addAll("Beginner", "Intermediate", "Advanced", "Expert");
        levelCombo.setValue("Intermediate");

        if (skill != null) {
            nameField.setText(skill.getName());
            levelCombo.setValue(skill.getLevel());
        }

        grid.add(new Label("Skill Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Proficiency Level:"), 0, 1);
        grid.add(levelCombo, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                Skill skl = skill != null ? skill : new Skill();
                skl.setName(nameField.getText());
                skl.setLevel(levelCombo.getValue());
                return skl;
            }
            return null;
        });

        Optional<Skill> result = dialog.showAndWait();
        result.ifPresent(skl -> {
            if (skill == null) {
                resume.getSkillList().add(skl);
                skillListView.getItems().add(skl);
            } else {
                skillListView.refresh();
            }
        });
    }

    private void showLanguageDialog(Language language) {
        Dialog<Language> dialog = new Dialog<>();
        dialog.setTitle(language == null ? "Add Language" : "Edit Language");
        dialog.setHeaderText(language == null ? "Enter language details" : "Update language details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Language name (e.g., English, Spanish)");
        ComboBox<String> proficiencyCombo = new ComboBox<>();
        proficiencyCombo.getItems().addAll("Native", "Fluent", "Intermediate", "Basic");
        proficiencyCombo.setValue("Intermediate");

        if (language != null) {
            nameField.setText(language.getName());
            proficiencyCombo.setValue(language.getProficiency());
        }

        grid.add(new Label("Language:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Proficiency:"), 0, 1);
        grid.add(proficiencyCombo, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                Language lang = language != null ? language : new Language();
                lang.setName(nameField.getText());
                lang.setProficiency(proficiencyCombo.getValue());
                return lang;
            }
            return null;
        });

        Optional<Language> result = dialog.showAndWait();
        result.ifPresent(lang -> {
            if (language == null) {
                resume.getLanguageList().add(lang);
                languageListView.getItems().add(lang);
            } else {
                languageListView.refresh();
            }
        });
    }

    private void showCertificationDialog(Certification certification) {
        Dialog<Certification> dialog = new Dialog<>();
        dialog.setTitle(certification == null ? "Add Certification" : "Edit Certification");
        dialog.setHeaderText(certification == null ? "Enter certification details" : "Update certification details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Certification Name");
        TextField organizationField = new TextField();
        organizationField.setPromptText("Issuing Organization");
        TextField issueDateField = new TextField();
        issueDateField.setPromptText("Jan 2023");
        TextField expiryDateField = new TextField();
        expiryDateField.setPromptText("Jan 2026 (optional)");
        TextField credentialIdField = new TextField();
        credentialIdField.setPromptText("Credential ID (optional)");
        TextField credentialUrlField = new TextField();
        credentialUrlField.setPromptText("Credential URL (optional)");

        if (certification != null) {
            nameField.setText(certification.getName());
            organizationField.setText(certification.getIssuingOrganization());
            issueDateField.setText(certification.getIssueDate());
            expiryDateField.setText(certification.getExpiryDate());
            credentialIdField.setText(certification.getCredentialId());
            credentialUrlField.setText(certification.getCredentialUrl());
        }

        grid.add(new Label("Certification Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Issuing Organization:"), 0, 1);
        grid.add(organizationField, 1, 1);
        grid.add(new Label("Issue Date:"), 0, 2);
        grid.add(issueDateField, 1, 2);
        grid.add(new Label("Expiry Date:"), 0, 3);
        grid.add(expiryDateField, 1, 3);
        grid.add(new Label("Credential ID:"), 0, 4);
        grid.add(credentialIdField, 1, 4);
        grid.add(new Label("Credential URL:"), 0, 5);
        grid.add(credentialUrlField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                Certification cert = certification != null ? certification : new Certification();
                cert.setName(nameField.getText());
                cert.setIssuingOrganization(organizationField.getText());
                cert.setIssueDate(issueDateField.getText());
                cert.setExpiryDate(expiryDateField.getText());
                cert.setCredentialId(credentialIdField.getText());
                cert.setCredentialUrl(credentialUrlField.getText());
                return cert;
            }
            return null;
        });

        Optional<Certification> result = dialog.showAndWait();
        result.ifPresent(cert -> {
            if (certification == null) {
                resume.getCertificationList().add(cert);
                certificationListView.getItems().add(cert);
            } else {
                certificationListView.refresh();
            }
        });
    }

    private void collectPersonalInfo() {
        PersonalInfo info = resume.getPersonalInfo();
        info.setFullName(fullNameField.getText());
        info.setEmail(emailField.getText());
        info.setPhone(phoneField.getText());
        info.setBirthDate(birthDatePicker.getValue());
        info.setAddress(addressField.getText());
        info.setCity(cityField.getText());
        info.setCountry(countryField.getText());
        info.setPostalCode(postalCodeField.getText());
        info.setLinkedIn(linkedInField.getText());
        info.setGithub(githubField.getText());
        info.setWebsite(websiteField.getText());
        info.setSummary(summaryArea.getText());
    }

    private void exportToPDF(Stage stage) {
        // Validate required fields
        if (fullNameField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error",
                     "Please fill in at least your name and email before exporting.");
            return;
        }

        // Collect personal information from form
        collectPersonalInfo();

        // Show file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Resume as PDF");
        fileChooser.setInitialFileName("Resume.pdf");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                PDFExportService exportService = new PDFExportService();
                exportService.exportToPDF(resume, file.getAbsolutePath());

                showAlert(Alert.AlertType.INFORMATION, "Success",
                         "Resume exported successfully to:\n" + file.getAbsolutePath());

            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Export Error",
                         "Failed to export resume: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void clearAllData() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Clear All Data");
        confirmation.setHeaderText("Are you sure you want to clear all data?");
        confirmation.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Clear personal info fields
            fullNameField.clear();
            emailField.clear();
            phoneField.clear();
            birthDatePicker.setValue(null);
            addressField.clear();
            cityField.clear();
            countryField.clear();
            postalCodeField.clear();
            linkedInField.clear();
            githubField.clear();
            websiteField.clear();
            summaryArea.clear();

            // Clear all lists
            resume.getEducationList().clear();
            educationListView.getItems().clear();

            resume.getExperienceList().clear();
            experienceListView.getItems().clear();

            resume.getProjectList().clear();
            projectListView.getItems().clear();

            resume.getSkillList().clear();
            skillListView.getItems().clear();

            resume.getLanguageList().clear();
            languageListView.getItems().clear();

            resume.getCertificationList().clear();
            certificationListView.getItems().clear();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
