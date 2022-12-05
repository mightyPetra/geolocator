package com.eurofins.locator.dto;

import com.eurofins.locator.utils.CountryUtils;
import lombok.Data;

@Data
public class IpLocationDTO {

    String ip;
    String city;
    String region;
    String country;
    String loc;
    String org;
    String postal;
    String timezone;

    public String getCountry() {
        return CountryUtils.getCountryNameByCode(country);
    }
}