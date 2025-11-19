package com.reproperty.controller;

import com.reproperty.model.Property;
import com.reproperty.model.ResidentialProperty;
import com.reproperty.model.CommercialProperty;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/properties")
@CrossOrigin(origins = "*")  // Allow CORS for frontend calls
public class PropertyController {

    private static final String FILE_PATH = "properties.txt";

    // Utility to read all properties from file
    private synchronized List<Property> readAllProperties() {
        List<Property> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if(!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null) {
                Property p = Property.fromFileString(line);
                if(p != null) list.add(p);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Utility to write all properties to file
    private synchronized void writeAllProperties(List<Property> properties) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH,false))) {
            for(Property p : properties) {
                pw.println(p.toFileString());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Create property
    @PostMapping("/add")
    public Property addProperty(@RequestBody Map<String, String> payload) {
        String location = payload.get("location");
        double price = Double.parseDouble(payload.get("price"));
        String type = payload.get("type");
        boolean available = Boolean.parseBoolean(payload.getOrDefault("available", "true"));

        Property newProperty;
        if ("Residential".equalsIgnoreCase(type)) {
            newProperty = new ResidentialProperty(location, price, available);
        } else if ("Commercial".equalsIgnoreCase(type)) {
            newProperty = new CommercialProperty(location, price, available);
        } else {
            // default to generic property
            newProperty = new Property(location, price, type, available);
        }

        List<Property> all = readAllProperties();
        all.add(newProperty);
        writeAllProperties(all);
        return newProperty;
    }

    // Read all or search properties: query params location, type, maxPrice
    @GetMapping("/search")
    public List<Property> searchProperties(
            @RequestParam(required=false) String location,
            @RequestParam(required=false) String type,
            @RequestParam(required=false) Double maxPrice) {

        List<Property> all = readAllProperties();

        return all.stream()
                .filter(p -> p.isAvailable())
                .filter(p -> location == null || p.getLocation().toLowerCase().contains(location.toLowerCase()))
                .filter(p -> type == null || p.getType().equalsIgnoreCase(type))
                .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    // Read single property by id
    @GetMapping("/{id}")
    public Property getProperty(@PathVariable String id) {
        List<Property> all = readAllProperties();
        return all.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    // Update property by id
    @PutMapping("/update/{id}")
    public Property updateProperty(@PathVariable String id, @RequestBody Map<String, String> payload) {
        List<Property> all = readAllProperties();
        for (Property p : all) {
            if (p.getId().equals(id)) {
                // Update fields if present in payload
                if(payload.containsKey("location")) p.setLocation(payload.get("location"));
                if(payload.containsKey("price")) p.setPrice(Double.parseDouble(payload.get("price")));
                if(payload.containsKey("type")) p.setType(payload.get("type"));
                if(payload.containsKey("available")) p.setAvailable(Boolean.parseBoolean(payload.get("available")));
                writeAllProperties(all);
                return p;
            }
        }
        return null;
    }

    // Delete property by id
    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteProperty(@PathVariable String id) {
        List<Property> all = readAllProperties();
        boolean removed = all.removeIf(p -> p.getId().equals(id));
        writeAllProperties(all);
        if(removed) {
            return Map.of("status", "success", "message", "Property removed");
        } else {
            return Map.of("status", "fail", "message", "Property not found");
        }
    }
}
