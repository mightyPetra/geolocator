package com.eurofins.locator.persistance.enities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "location")
public class LocationEntity {

    @Id
    private String id;
    private String country;
    private String city;
    private Double[] loc;

    public void setLoc(String loc) {
        setLoc(StringToCoordinates(loc));
    }

    public void setLoc(Double[] loc) {
        this.loc = loc;
    }

    private Double[] StringToCoordinates(String loc) {
        Double[] locArray = new Double[2];
        String[] splitLoc = loc.split(",");
        for (int i = 0; splitLoc.length == 2 && i < splitLoc.length; i++) {
            try {
                locArray[i] = Double.valueOf(splitLoc[i]);
            } catch (NumberFormatException ex) {
                return new Double[2];
            }
        }
        return locArray;
    }

}
