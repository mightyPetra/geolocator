package com.eurofins.locator.services;

import com.eurofins.locator.configs.Properties;
import com.eurofins.locator.dto.UniversityDTO;
import com.eurofins.locator.utils.CountryUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UniLocatorService {

    private final RestTemplate restTemplate;
    private final String uniApiUrl;

    @Autowired
    public UniLocatorService(RestTemplate restTemplate, Properties properties) {
        this.restTemplate = restTemplate;
        this.uniApiUrl = properties.getUnisApiUrl();
    }

    public List<UniversityDTO> getListOfUnisByCountry(String country) {
        if (country.isEmpty() || CountryUtils.validateCountry(country)) {
            String requestString = String.format("%ssearch?country=%s", uniApiUrl, country);
            var responseEntity = restTemplate.getForEntity(requestString, UniversityDTO[].class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                log.info("Returning list of universities for {}", country.isEmpty() ? "the whole world" : country);
                UniversityDTO[] arrayOfUnis = Objects.requireNonNull(responseEntity.getBody());
                return Arrays.stream(arrayOfUnis).toList();
            }
        }
        log.warn("No universities were found for {}", country);
        return Collections.emptyList();
    }
}