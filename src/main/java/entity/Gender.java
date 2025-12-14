package main.java.entity;

public enum Gender {
    M("ÄÐ"), F("Å®");

    private String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}