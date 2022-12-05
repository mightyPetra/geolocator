package com.eurofins.locator.utils;

import java.util.*;
import java.util.Locale;

public class CountryUtils {

    private static final Map<String, String> countriesMap = new HashMap<>();

    static {

        Arrays.stream(Locale.getISOCountries())
                .forEach(l -> countriesMap.put(l, new Locale("", l).getDisplayCountry()));
    }

    public static String getCountryNameByCode(String code) {
        return Optional.ofNullable(countriesMap.get(code)).orElse("Unknown");
    }

    public static boolean validateCountry(String country) {
        return countriesMap
                .values()
                .stream()
                .anyMatch(v -> v.equalsIgnoreCase(country));
    }

}
