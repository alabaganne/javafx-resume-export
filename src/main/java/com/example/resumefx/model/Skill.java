package com.example.resumefx.model;

public class Skill {
    private String name;
    private String level; // Beginner, Intermediate, Advanced, Expert

    public Skill() {
        this.level = "Intermediate";
    }

    public Skill(String name, String level) {
        this.name = name;
        this.level = level;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return name + " (" + level + ")";
    }
}
