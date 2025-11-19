package com.reproperty.model;

public class ResidentialProperty extends Property {
    // Additional specific fields can go here, for simplicity we leave empty
    public ResidentialProperty() {
        super();
        setType("Residential");
    }

    public ResidentialProperty(String location, double price, boolean available) {
        super(location, price, "Residential", available);
    }
}
