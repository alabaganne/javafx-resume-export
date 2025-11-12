package com.example.resumefx.service;

import com.example.resumefx.model.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class PDFExportService {

    private static final PDFont FONT_BOLD = PDType1Font.HELVETICA_BOLD;
    private static final PDFont FONT_REGULAR = PDType1Font.HELVETICA;
    private static final PDFont FONT_ITALIC = PDType1Font.HELVETICA_OBLIQUE;

    private static final float MARGIN = 50;
    private static final float FONT_SIZE_TITLE = 24;
    private static final float FONT_SIZE_HEADING = 16;
    private static final float FONT_SIZE_SUBHEADING = 12;
    private static final float FONT_SIZE_REGULAR = 10;
    private static final float LINE_HEIGHT = 15;

    private PDDocument document;
    private PDPage currentPage;
    private PDPageContentStream contentStream;
    private float yPosition;

    public void exportToPDF(Resume resume, String filePath) throws IOException {
        document = new PDDocument();
        createNewPage();

        try {
            // Personal Information Header
            writePersonalInfo(resume.getPersonalInfo());

            // Summary
            if (resume.getPersonalInfo().getSummary() != null &&
                !resume.getPersonalInfo().getSummary().trim().isEmpty()) {
                addSection("PROFESSIONAL SUMMARY");
                writeText(resume.getPersonalInfo().getSummary(), FONT_REGULAR, FONT_SIZE_REGULAR);
                addVerticalSpace(10);
            }

            // Education
            if (!resume.getEducationList().isEmpty()) {
                addSection("EDUCATION");
                for (Education edu : resume.getEducationList()) {
                    writeEducation(edu);
                }
                addVerticalSpace(10);
            }

            // Experience
            if (!resume.getExperienceList().isEmpty()) {
                addSection("WORK EXPERIENCE");
                for (Experience exp : resume.getExperienceList()) {
                    writeExperience(exp);
                }
                addVerticalSpace(10);
            }

            // Projects
            if (!resume.getProjectList().isEmpty()) {
                addSection("PROJECTS");
                for (Project project : resume.getProjectList()) {
                    writeProject(project);
                }
                addVerticalSpace(10);
            }

            // Skills
            if (!resume.getSkillList().isEmpty()) {
                addSection("SKILLS");
                writeSkills(resume.getSkillList());
                addVerticalSpace(10);
            }

            // Languages
            if (!resume.getLanguageList().isEmpty()) {
                addSection("LANGUAGES");
                writeLanguages(resume.getLanguageList());
                addVerticalSpace(10);
            }

            // Certifications
            if (!resume.getCertificationList().isEmpty()) {
                addSection("CERTIFICATIONS");
                for (Certification cert : resume.getCertificationList()) {
                    writeCertification(cert);
                }
            }

            contentStream.close();
            document.save(filePath);
            document.close();

        } catch (IOException e) {
            if (contentStream != null) {
                contentStream.close();
            }
            if (document != null) {
                document.close();
            }
            throw e;
        }
    }

    private void createNewPage() throws IOException {
        if (contentStream != null) {
            contentStream.close();
        }
        currentPage = new PDPage(PDRectangle.A4);
        document.addPage(currentPage);
        contentStream = new PDPageContentStream(document, currentPage);
        yPosition = currentPage.getMediaBox().getHeight() - MARGIN;
    }

    private void checkNewPage() throws IOException {
        if (yPosition < MARGIN + 50) {
            createNewPage();
        }
    }

    private void writePersonalInfo(PersonalInfo info) throws IOException {
        // Name
        contentStream.setFont(FONT_BOLD, FONT_SIZE_TITLE);
        contentStream.setNonStrokingColor(Color.decode("#1e293b"));
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        String name = info.getFullName() != null ? info.getFullName() : "Your Name";
        contentStream.showText(name);
        contentStream.endText();
        yPosition -= 30;

        // Contact Information
        contentStream.setFont(FONT_REGULAR, FONT_SIZE_REGULAR);
        contentStream.setNonStrokingColor(Color.decode("#475569"));

        StringBuilder contactLine = new StringBuilder();
        if (info.getEmail() != null && !info.getEmail().isEmpty()) {
            contactLine.append(info.getEmail()).append("  |  ");
        }
        if (info.getPhone() != null && !info.getPhone().isEmpty()) {
            contactLine.append(info.getPhone()).append("  |  ");
        }
        if (info.getCity() != null && !info.getCity().isEmpty()) {
            contactLine.append(info.getCity());
            if (info.getCountry() != null && !info.getCountry().isEmpty()) {
                contactLine.append(", ").append(info.getCountry());
            }
        }

        if (contactLine.length() > 0) {
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText(contactLine.toString());
            contentStream.endText();
            yPosition -= LINE_HEIGHT;
        }

        // Links
        StringBuilder linksLine = new StringBuilder();
        if (info.getLinkedIn() != null && !info.getLinkedIn().isEmpty()) {
            linksLine.append("LinkedIn: ").append(info.getLinkedIn()).append("  |  ");
        }
        if (info.getGithub() != null && !info.getGithub().isEmpty()) {
            linksLine.append("GitHub: ").append(info.getGithub()).append("  |  ");
        }
        if (info.getWebsite() != null && !info.getWebsite().isEmpty()) {
            linksLine.append("Website: ").append(info.getWebsite());
        }

        if (linksLine.length() > 0) {
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText(linksLine.toString());
            contentStream.endText();
            yPosition -= LINE_HEIGHT;
        }

        // Separator line
        addVerticalSpace(5);
        contentStream.setStrokingColor(Color.decode("#e2e8f0"));
        contentStream.setLineWidth(1.5f);
        contentStream.moveTo(MARGIN, yPosition);
        contentStream.lineTo(currentPage.getMediaBox().getWidth() - MARGIN, yPosition);
        contentStream.stroke();
        addVerticalSpace(15);
    }

    private void addSection(String title) throws IOException {
        checkNewPage();
        contentStream.setFont(FONT_BOLD, FONT_SIZE_HEADING);
        contentStream.setNonStrokingColor(Color.decode("#2563eb"));
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        contentStream.showText(title);
        contentStream.endText();
        yPosition -= 20;
    }

    private void writeEducation(Education edu) throws IOException {
        checkNewPage();

        // Degree and Institution
        contentStream.setFont(FONT_BOLD, FONT_SIZE_SUBHEADING);
        contentStream.setNonStrokingColor(Color.decode("#1e293b"));
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        String degreeText = (edu.getDegree() != null ? edu.getDegree() : "") +
                           (edu.getFieldOfStudy() != null ? " in " + edu.getFieldOfStudy() : "");
        contentStream.showText(degreeText);
        contentStream.endText();
        yPosition -= LINE_HEIGHT;

        // Institution and Date
        contentStream.setFont(FONT_ITALIC, FONT_SIZE_REGULAR);
        contentStream.setNonStrokingColor(Color.decode("#475569"));
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        String instText = (edu.getInstitution() != null ? edu.getInstitution() : "") +
                         " | " + formatDateRange(edu.getStartDate(), edu.getEndDate(), edu.isCurrent());
        contentStream.showText(instText);
        contentStream.endText();
        yPosition -= LINE_HEIGHT;

        // Grade
        if (edu.getGrade() != null && !edu.getGrade().isEmpty()) {
            contentStream.setFont(FONT_REGULAR, FONT_SIZE_REGULAR);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Grade: " + edu.getGrade());
            contentStream.endText();
            yPosition -= LINE_HEIGHT;
        }

        // Description
        if (edu.getDescription() != null && !edu.getDescription().isEmpty()) {
            writeText(edu.getDescription(), FONT_REGULAR, FONT_SIZE_REGULAR);
        }

        addVerticalSpace(8);
    }

    private void writeExperience(Experience exp) throws IOException {
        checkNewPage();

        // Job Title
        contentStream.setFont(FONT_BOLD, FONT_SIZE_SUBHEADING);
        contentStream.setNonStrokingColor(Color.decode("#1e293b"));
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        contentStream.showText(exp.getJobTitle() != null ? exp.getJobTitle() : "");
        contentStream.endText();
        yPosition -= LINE_HEIGHT;

        // Company and Date
        contentStream.setFont(FONT_ITALIC, FONT_SIZE_REGULAR);
        contentStream.setNonStrokingColor(Color.decode("#475569"));
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        String companyText = (exp.getCompany() != null ? exp.getCompany() : "") +
                            (exp.getLocation() != null ? ", " + exp.getLocation() : "") +
                            " | " + formatDateRange(exp.getStartDate(), exp.getEndDate(), exp.isCurrent());
        contentStream.showText(companyText);
        contentStream.endText();
        yPosition -= LINE_HEIGHT;

        // Description
        if (exp.getDescription() != null && !exp.getDescription().isEmpty()) {
            writeText(exp.getDescription(), FONT_REGULAR, FONT_SIZE_REGULAR);
        }

        addVerticalSpace(8);
    }

    private void writeProject(Project project) throws IOException {
        checkNewPage();

        // Project Name
        contentStream.setFont(FONT_BOLD, FONT_SIZE_SUBHEADING);
        contentStream.setNonStrokingColor(Color.decode("#1e293b"));
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        contentStream.showText(project.getName() != null ? project.getName() : "");
        contentStream.endText();
        yPosition -= LINE_HEIGHT;

        // Role and Technologies
        contentStream.setFont(FONT_ITALIC, FONT_SIZE_REGULAR);
        contentStream.setNonStrokingColor(Color.decode("#475569"));
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        String roleText = (project.getRole() != null ? project.getRole() + " | " : "") +
                         (project.getTechnologies() != null ? project.getTechnologies() : "");
        contentStream.showText(roleText);
        contentStream.endText();
        yPosition -= LINE_HEIGHT;

        // Description
        if (project.getDescription() != null && !project.getDescription().isEmpty()) {
            writeText(project.getDescription(), FONT_REGULAR, FONT_SIZE_REGULAR);
        }

        // URL
        if (project.getUrl() != null && !project.getUrl().isEmpty()) {
            contentStream.setFont(FONT_REGULAR, FONT_SIZE_REGULAR);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("URL: " + project.getUrl());
            contentStream.endText();
            yPosition -= LINE_HEIGHT;
        }

        addVerticalSpace(8);
    }

    private void writeSkills(java.util.List<Skill> skills) throws IOException {
        checkNewPage();

        contentStream.setFont(FONT_REGULAR, FONT_SIZE_REGULAR);
        contentStream.setNonStrokingColor(Color.decode("#1e293b"));

        StringBuilder skillText = new StringBuilder();
        for (int i = 0; i < skills.size(); i++) {
            Skill skill = skills.get(i);
            skillText.append(skill.getName());
            if (skill.getLevel() != null && !skill.getLevel().isEmpty()) {
                skillText.append(" (").append(skill.getLevel()).append(")");
            }
            if (i < skills.size() - 1) {
                skillText.append(", ");
            }
        }

        writeText(skillText.toString(), FONT_REGULAR, FONT_SIZE_REGULAR);
    }

    private void writeLanguages(java.util.List<Language> languages) throws IOException {
        checkNewPage();

        contentStream.setFont(FONT_REGULAR, FONT_SIZE_REGULAR);
        contentStream.setNonStrokingColor(Color.decode("#1e293b"));

        StringBuilder langText = new StringBuilder();
        for (int i = 0; i < languages.size(); i++) {
            Language lang = languages.get(i);
            langText.append(lang.getName());
            if (lang.getProficiency() != null && !lang.getProficiency().isEmpty()) {
                langText.append(" (").append(lang.getProficiency()).append(")");
            }
            if (i < languages.size() - 1) {
                langText.append(", ");
            }
        }

        writeText(langText.toString(), FONT_REGULAR, FONT_SIZE_REGULAR);
    }

    private void writeCertification(Certification cert) throws IOException {
        checkNewPage();

        // Certification Name
        contentStream.setFont(FONT_BOLD, FONT_SIZE_SUBHEADING);
        contentStream.setNonStrokingColor(Color.decode("#1e293b"));
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        contentStream.showText(cert.getName() != null ? cert.getName() : "");
        contentStream.endText();
        yPosition -= LINE_HEIGHT;

        // Issuing Organization and Date
        contentStream.setFont(FONT_ITALIC, FONT_SIZE_REGULAR);
        contentStream.setNonStrokingColor(Color.decode("#475569"));
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        String certText = (cert.getIssuingOrganization() != null ? cert.getIssuingOrganization() : "") +
                         (cert.getIssueDate() != null ? " | Issued: " + cert.getIssueDate() : "");
        contentStream.showText(certText);
        contentStream.endText();
        yPosition -= LINE_HEIGHT;

        // Credential ID
        if (cert.getCredentialId() != null && !cert.getCredentialId().isEmpty()) {
            contentStream.setFont(FONT_REGULAR, FONT_SIZE_REGULAR);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Credential ID: " + cert.getCredentialId());
            contentStream.endText();
            yPosition -= LINE_HEIGHT;
        }

        addVerticalSpace(8);
    }

    private void writeText(String text, PDFont font, float fontSize) throws IOException {
        if (text == null || text.isEmpty()) return;

        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(Color.decode("#1e293b"));

        float maxWidth = currentPage.getMediaBox().getWidth() - (2 * MARGIN);
        String[] lines = wrapText(text, font, fontSize, maxWidth);

        for (String line : lines) {
            checkNewPage();
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText(line);
            contentStream.endText();
            yPosition -= LINE_HEIGHT;
        }
    }

    private String[] wrapText(String text, PDFont font, float fontSize, float maxWidth) throws IOException {
        java.util.List<String> lines = new java.util.ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;
            float width = font.getStringWidth(testLine) / 1000 * fontSize;

            if (width > maxWidth && currentLine.length() > 0) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines.toArray(new String[0]);
    }

    private void addVerticalSpace(float space) {
        yPosition -= space;
    }

    private String formatDateRange(String startDate, String endDate, boolean current) {
        String start = startDate != null && !startDate.isEmpty() ? startDate : "Present";
        String end = current ? "Present" : (endDate != null && !endDate.isEmpty() ? endDate : "Present");
        return start + " - " + end;
    }
}
