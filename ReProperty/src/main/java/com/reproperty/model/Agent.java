package com.reproperty.model;

public class Agent {
    private String id;
    private String name;
    private double rating;

    public Agent(String id, String name, double rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getRating() { return rating; }

    public void setRating(double rating) { this.rating = rating; }
}
