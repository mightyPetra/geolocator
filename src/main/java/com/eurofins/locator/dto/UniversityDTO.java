package com.eurofins.locator.dto;

import lombok.Data;

import java.net.URL;
import java.util.List;

@Data
public class UniversityDTO {
    String country;
    List<String> domains;
    List<URL> webPages;
    String alphaTwoCode;
    String name;
    String stateProvince;

}