package com.eurofins.locator.persistance.enities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationEntityTest {

    private LocationEntity location;

    @BeforeEach
    void setUp() {
        location = new LocationEntity();
    }

    @Test
    void setValidLoc() {
        String locationString = "1.2,1.3";
        location.setLoc(locationString);
        assertArrayEquals(new Double[]{1.2, 1.3}, location.getLoc());
    }

    @Test
    void setLocFromMalformedString() {
        String locationString = "12313";
        location.setLoc(locationString);
        assertArrayEquals(new Double[2], location.getLoc());
    }

    @Test
    void setLocFromLongerString() {
        String locationString = "123, 456, 689";
        location.setLoc(locationString);
        assertArrayEquals(new Double[2], location.getLoc());
    }

    @Test
    void setLocFromAlphaNumericString() {
        String locationString = "1.234, A";
        location.setLoc(locationString);
        assertArrayEquals(new Double[2], location.getLoc());
    }
}