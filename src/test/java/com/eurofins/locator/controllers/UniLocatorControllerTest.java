package com.eurofins.locator.controllers;

import com.eurofins.locator.dto.UniversityDTO;
import com.eurofins.locator.services.UniLocatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UniLocatorControllerTest {

    @InjectMocks
    private UniLocatorController uniLocatorController;

    @Mock
    private UniLocatorService uniLocatorService;

    private List<UniversityDTO> listOfUnis;

    @BeforeEach
    void setUp() {
        UniversityDTO narniaUni = uniBuilder("Narnia");
        UniversityDTO shireUni = uniBuilder("Shire");
        listOfUnis = List.of(narniaUni, shireUni);
    }

    @Test
    void getListOfUnisByCountry() {
        String narnia = "Narnia";

        when(uniLocatorService.getListOfUnisByCountry(narnia))
                .thenReturn(List.of(listOfUnis.get(0)));

        ResponseEntity<List<UniversityDTO>> response = uniLocatorController.getListOfUnisByIP(narnia);

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(narnia, response.getBody().get(0).getCountry());
    }

    @Test
    void getListOfUnisNullCountry() {
        when(uniLocatorService.getListOfUnisByCountry(null))
                .thenReturn(listOfUnis);

        ResponseEntity<List<UniversityDTO>> response = uniLocatorController.getListOfUnisByIP(null);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Narnia", response.getBody().get(0).getCountry());
        Assertions.assertEquals("Shire", response.getBody().get(1).getCountry());
    }

    @Test
    void getListOfUnisUnknownCountry() {
        String hyrule = "Hyrule";
        when(uniLocatorService.getListOfUnisByCountry(hyrule))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<UniversityDTO>> response = uniLocatorController.getListOfUnisByIP(hyrule);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(response.getBody().isEmpty());
    }

    private UniversityDTO uniBuilder(String country) {
        UniversityDTO uni = new UniversityDTO();
        uni.setCountry(country);
        return uni;
    }
}