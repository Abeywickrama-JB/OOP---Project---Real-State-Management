package com.reproperty.model;

public class CommercialProperty extends Property {
    // Additional specific fields can go here, for simplicity we leave empty
    public CommercialProperty() {
        super();
        setType("Commercial");
    }

    public CommercialProperty(String location, double price, boolean available) {
        super(location, price, "Commercial", available);
    }
}
