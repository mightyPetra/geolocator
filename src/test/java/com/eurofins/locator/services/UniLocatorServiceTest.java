package com.eurofins.locator.services;

import com.eurofins.locator.configs.Properties;
import com.eurofins.locator.dto.UniversityDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UniLocatorServiceTest {

    private final String url = "http://universities.hipolabs.com/";
    private final String requestUrl = url+"search?country=";
    UniversityDTO[] arrayOfUnis = new UniversityDTO[]{new UniversityDTO()};
    private UniLocatorService uniLocatorService;

    @Mock
    RestTemplate restTemplate;
    @Mock
    Properties properties;

    @BeforeEach
    void setup() {
        when(properties.getUnisApiUrl()).thenReturn(url);
        uniLocatorService = new UniLocatorService(restTemplate, properties);
    }

    @Test
    void getListOfUnisByValidCountry() {
        when(restTemplate.getForEntity(requestUrl+"Poland", UniversityDTO[].class)).thenReturn(ResponseEntity.ok(arrayOfUnis));
        List<UniversityDTO> result = uniLocatorService.getListOfUnisByCountry("Poland");
        Assertions.assertEquals(1, result.size());
    }
    @Test
    void getListOfUnisByEmptyCountry() {
        when(restTemplate.getForEntity(requestUrl, UniversityDTO[].class)).thenReturn(ResponseEntity.ok(arrayOfUnis));
        List<UniversityDTO> result = uniLocatorService.getListOfUnisByCountry("");
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void getListOfUnisByInvalidCountry() {
        List<UniversityDTO> result = uniLocatorService.getListOfUnisByCountry("PL");
        Assertions.assertTrue(result.isEmpty());
    }
}