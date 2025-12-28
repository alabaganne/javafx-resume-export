package com.example.resumefx.model;

public class Language {
    private String name;
    private String proficiency; // Native, Fluent, Intermediate, Basic

    public Language() {
        this.proficiency = "Intermediate";
    }

    public Language(String name, String proficiency) {
        this.name = name;
        this.proficiency = proficiency;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    @Override
    public String toString() {
        return name + " (" + proficiency + ")";
    }
}
