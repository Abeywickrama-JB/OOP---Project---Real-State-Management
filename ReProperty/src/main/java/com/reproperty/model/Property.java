package com.reproperty.model;

import java.util.UUID;

public class Property {
    private String id;
    private String location;
    private double price;
    private String type; // Residential or Commercial
    private boolean available;

    public Property() {
        this.id = UUID.randomUUID().toString();
    }

    public Property(String location, double price, String type, boolean available) {
        this.id = UUID.randomUUID().toString();
        this.location = location;
        this.price = price;
        this.type = type;
        this.available = available;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    // no setId - id is immutable

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // To string format for saving to file (CSV-like)
    public String toFileString() {
        return String.join(",",
                id,
                location.replace(",", " "),
                String.valueOf(price),
                type,
                String.valueOf(available));
    }

    // Parse from line in file
    public static Property fromFileString(String line) {
        try {
            String[] parts = line.split(",");
            if(parts.length < 5) return null;
            Property p = new Property();
            p.id = parts[0];
            p.location = parts[1];
            p.price = Double.parseDouble(parts[2]);
            p.type = parts[3];
            p.available = Boolean.parseBoolean(parts[4]);
            return p;
        } catch(Exception e) {
            return null;
        }
    }
}
