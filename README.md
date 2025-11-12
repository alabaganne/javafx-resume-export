# Resume Builder

A modern, professional JavaFX desktop application for building and exporting resumes to PDF format.

## Features

### Comprehensive Resume Sections
- **Personal Information**: Name, contact details, professional links (LinkedIn, GitHub, website), and professional summary
- **Education**: Add multiple education entries with degree, institution, field of study, dates, grade, and descriptions
- **Work Experience**: Document your work history with job titles, companies, locations, dates, and detailed descriptions
- **Projects**: Showcase your projects with names, technologies used, roles, URLs, and descriptions
- **Skills**: List your technical and soft skills with proficiency levels (Beginner, Intermediate, Advanced, Expert)
- **Languages**: Specify spoken languages with proficiency levels (Native, Fluent, Intermediate, Basic)
- **Certifications**: Add professional certifications with issuing organizations, dates, and credential information

### Modern User Interface
- Clean, professional design with a modern color scheme
- Tab-based navigation for easy section switching
- Intuitive forms with helpful placeholders
- Responsive dialogs for adding/editing entries
- Smooth scrolling and professional spacing

### PDF Export
- Professional PDF generation using Apache PDFBox
- Well-formatted, clean resume layout
- Proper typography and spacing
- Section headers with visual hierarchy
- Automatic text wrapping and page breaks

### User Experience
- Form validation to ensure required fields are filled
- Confirmation dialogs for data clearing
- Success/error notifications
- Easy entry management (add, edit, delete)
- Clear visual feedback for user actions

## Technical Stack

- **Language**: Java 17
- **Framework**: JavaFX 17
- **Build Tool**: Maven
- **PDF Library**: Apache PDFBox 2.0.27
- **Architecture**: Model-View separation with service layer

## Project Structure

```
javafx-resume-export/
├── src/main/
│   ├── java/com/example/resumefx/
│   │   ├── Main.java                    # Main application entry point
│   │   ├── model/                       # Data models
│   │   │   ├── Resume.java              # Main resume container
│   │   │   ├── PersonalInfo.java        # Personal information model
│   │   │   ├── Education.java           # Education entry model
│   │   │   ├── Experience.java          # Work experience model
│   │   │   ├── Project.java             # Project model
│   │   │   ├── Skill.java               # Skill model
│   │   │   ├── Language.java            # Language model
│   │   │   └── Certification.java       # Certification model
│   │   └── service/
│   │       └── PDFExportService.java    # PDF generation service
│   └── resources/
│       └── style.css                     # Application styling
├── pom.xml                               # Maven configuration
└── README.md                             # This file
```

## Requirements

- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher
- Minimum 2GB RAM
- 100MB disk space

## Building and Running

### Using Maven

1. **Clean and compile the project**:
   ```bash
   mvn clean compile
   ```

2. **Run the application**:
   ```bash
   mvn javafx:run
   ```

3. **Package as JAR** (optional):
   ```bash
   mvn clean package
   ```

### Development

The project uses Maven for dependency management. All dependencies will be automatically downloaded on first build:

- JavaFX Controls 17.0.1
- JavaFX FXML 17.0.1
- Apache PDFBox 2.0.27

## Usage Guide

### 1. Personal Information Tab
- Fill in your basic information (name and email are required)
- Add professional links to LinkedIn, GitHub, or personal website
- Write a brief professional summary (2-3 sentences recommended)

### 2. Education Tab
- Click "Add Education" to add a new education entry
- Fill in degree, institution, field of study, and dates
- Use "Currently studying here" checkbox for ongoing education
- Edit or delete entries using the respective buttons

### 3. Experience Tab
- Click "Add Experience" to add work experience
- Include job title, company, location, and date range
- Describe your responsibilities and achievements
- Use "I currently work here" for your current position

### 4. Projects Tab
- Add your significant projects
- Specify your role and technologies used
- Include project URLs (GitHub, live demo, etc.)

### 5. Skills Tab
- Add technical and soft skills
- Select proficiency level for each skill
- Skills will be grouped in the PDF output

### 6. Languages Tab
- List languages you speak
- Specify proficiency level for each

### 7. Certifications Tab
- Add professional certifications
- Include issuing organization and dates
- Add credential ID and URL for verification

### 8. Exporting Your Resume
1. Fill in at least your name and email (required fields)
2. Click "Export as PDF" button at the bottom
3. Choose a location and filename for your PDF
4. Your professional resume will be generated instantly

## UI Features

### Design Highlights
- **Modern Color Scheme**: Professional blue gradient header with clean white content areas
- **Visual Hierarchy**: Clear section titles and organized content
- **Responsive Forms**: Grid-based layouts that adapt to content
- **Professional Buttons**: Color-coded buttons (blue for primary actions, red for destructive actions)
- **Smooth Interactions**: Hover effects and visual feedback

### Keyboard Navigation
- Tab through form fields naturally
- Enter to confirm dialogs
- Escape to cancel operations

## Data Management

### Adding Entries
All sections with multiple entries (Education, Experience, Projects, Skills, Languages, Certifications) support:
- **Add**: Create new entries via dialog forms
- **Edit**: Modify existing entries by selecting and clicking "Edit Selected"
- **Delete**: Remove entries with confirmation

### Data Validation
- Required fields are marked with asterisks (*)
- Email and name validation before PDF export
- Helpful error messages guide you to fix issues

### Clear All Data
- Use "Clear All" button to reset the entire form
- Confirmation dialog prevents accidental data loss

## PDF Output

The generated PDF includes:
- Professional header with your name and contact information
- Well-organized sections with clear headings
- Consistent formatting and typography
- Proper spacing and margins
- Automatic pagination for longer resumes

### PDF Styling
- **Font**: Helvetica family (regular, bold, italic)
- **Colors**: Professional blue for headers, dark gray for content
- **Layout**: Standard A4 size with proper margins
- **Sections**: Clearly separated with visual hierarchy

## Troubleshooting

### Application Won't Start
- Ensure Java 17 or higher is installed: `java -version`
- Check Maven is configured correctly: `mvn -version`
- Verify JavaFX modules are available

### PDF Export Fails
- Ensure you have write permissions to the selected directory
- Check that required fields (name, email) are filled
- Make sure Apache PDFBox dependencies are downloaded

### Styling Issues
- Ensure `style.css` is in `src/main/resources/`
- Check console for CSS loading errors
- Verify resource path in Main.java

## Contributing

This is a complete resume builder application. Future enhancements could include:
- Data persistence (save/load resume data)
- Multiple resume templates
- Custom color themes
- Export to other formats (HTML, DOCX)
- Resume preview before export
- Spell checking
- Auto-save functionality

## License

This project is provided as-is for educational and personal use.

## Version History

### Version 1.0 (Current)
- Complete resume builder with 7 sections
- Modern tabbed UI with professional styling
- PDF export functionality
- Form validation and user feedback
- Add/Edit/Delete functionality for all sections
- Professional PDF formatting

---

**Built with JavaFX** | **Powered by Apache PDFBox**
