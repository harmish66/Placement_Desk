package com.example.placement_desk.Model;

public class Year {
    private String year;

    public Year() {
        // Default constructor required for Firebase
    }

    public Year(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }
}
