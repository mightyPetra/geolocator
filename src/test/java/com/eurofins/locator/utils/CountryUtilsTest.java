package com.eurofins.locator.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryUtilsTest {

    @Test
    void getCountry() {
        assertEquals("Latvia", CountryUtils.getCountryNameByCode("LV"));
    }

    @Test
    void getNonExistentCountry() {
        assertEquals("Unknown", CountryUtils.getCountryNameByCode("Narnia"));
    }

    @Test
    void validateValidCountryName() {
        assertTrue(CountryUtils.validateCountry("Estonia"));
    }

    @Test
    void validateInvalidCountryName() {
        assertFalse(CountryUtils.validateCountry("Narnia"));
    }
}