package com.example.resumefx.model;

import java.util.ArrayList;
import java.util.List;

public class Resume {
    private PersonalInfo personalInfo;
    private List<Education> educationList;
    private List<Experience> experienceList;
    private List<Project> projectList;
    private List<Skill> skillList;
    private List<Language> languageList;
    private List<Certification> certificationList;

    public Resume() {
        this.personalInfo = new PersonalInfo();
        this.educationList = new ArrayList<>();
        this.experienceList = new ArrayList<>();
        this.projectList = new ArrayList<>();
        this.skillList = new ArrayList<>();
        this.languageList = new ArrayList<>();
        this.certificationList = new ArrayList<>();
    }

    // Getters and Setters
    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public List<Education> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<Education> educationList) {
        this.educationList = educationList;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

    public List<Language> getLanguageList() {
        return languageList;
    }

    public void setLanguageList(List<Language> languageList) {
        this.languageList = languageList;
    }

    public List<Certification> getCertificationList() {
        return certificationList;
    }

    public void setCertificationList(List<Certification> certificationList) {
        this.certificationList = certificationList;
    }
}
