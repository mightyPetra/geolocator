package com.eurofins.locator.services;

import com.eurofins.locator.configs.Properties;
import com.eurofins.locator.dto.IpLocationDTO;
import com.eurofins.locator.persistance.enities.IpEntity;
import com.eurofins.locator.persistance.enities.LocationEntity;
import com.eurofins.locator.persistance.repositories.IpRepository;
import com.eurofins.locator.persistance.repositories.LocationRepository;
import com.eurofins.locator.services.exceptions.IpFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class GeolocatorServiceTest {

    private final String url = "https://ipinfo.io/";
    private final String ip = "127.1.1.0";
    private final Double[] loc = new Double[]{5.123,4.321};

    private GeolocatorService geolocatorService;

    @Mock
    IpRepository ipRepository;
    @Mock
    LocationRepository locationRepository;
    @Mock
    Properties properties;
    @Mock
    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {

        Mockito.when(properties.getIpApiUrl()).thenReturn(url);
        geolocatorService = new GeolocatorService(ipRepository, locationRepository, restTemplate, properties);
    }

    @Test
    void getLocationByIpWithoutIpInDB() {
        ResponseEntity<IpLocationDTO> entity = ResponseEntity.ok(buildDto());
        Mockito.when(restTemplate.getForEntity(url+ip+"/geo", IpLocationDTO.class)).thenReturn(entity);
        Mockito.when(locationRepository.findByLoc(loc)).thenReturn(Optional.empty());
        Mockito.when(ipRepository.existsByIp(ip)).thenReturn(false);

        geolocatorService.getLocationByIp(ip);
        Mockito.verify(ipRepository, Mockito.times(1)).save(Mockito.any(IpEntity.class));
        Mockito.verify(locationRepository, Mockito.times(1)).save(Mockito.any(LocationEntity.class));
    }

    @Test
    void getLocationByIpWithIpInDB() {
        ResponseEntity<IpLocationDTO> entity = ResponseEntity.ok(buildDto());
        Mockito.when(restTemplate.getForEntity(url+ip+"/geo", IpLocationDTO.class)).thenReturn(entity);
        Mockito.when(ipRepository.existsByIp(ip)).thenReturn(true);

        geolocatorService.getLocationByIp(ip);
        Mockito.verify(ipRepository, Mockito.times(0)).save(Mockito.any(IpEntity.class));
        Mockito.verify(locationRepository, Mockito.times(0)).save(Mockito.any(LocationEntity.class));

    }

    @Test
    void getLocationByIpWithLocationAlreadyInDB() {
        Optional<LocationEntity> location = Optional.of(buildLocationEntity());
        ResponseEntity<IpLocationDTO> entity = ResponseEntity.ok(buildDto());

        Mockito.when(restTemplate.getForEntity(url+ip+"/geo", IpLocationDTO.class)).thenReturn(entity);
        Mockito.when(locationRepository.findByLoc(loc)).thenReturn(location);
        Mockito.when(ipRepository.existsByIp(ip)).thenReturn(false);

        geolocatorService.getLocationByIp(ip);
        Mockito.verify(ipRepository, Mockito.times(1)).save(Mockito.any(IpEntity.class));
        Mockito.verify(locationRepository, Mockito.times(0)).save(Mockito.any(LocationEntity.class));
    }

    @Test
    void getLocationByMalformedIp() {
        String brokenIp = "123";
        Mockito.when(restTemplate.getForEntity(url+brokenIp+"/geo", IpLocationDTO.class)).thenThrow(HttpClientErrorException.NotFound.class);
        Assertions.assertThrows(IpFormatException.class, () -> geolocatorService.getLocationByIp(brokenIp));
    }

    private IpLocationDTO buildDto() {
        IpLocationDTO dto = new IpLocationDTO();
        dto.setIp(ip);
        dto.setCity("Riga");
        dto.setRegion("Riga");
        dto.setCountry("LV");
        dto.setLoc(String.format("%s,%s", loc[0], loc[1]));
        dto.setOrg("Foo");
        dto.setPostal("1111");
        dto.setTimezone("Europe/Riga");

        return dto;
    }

    private LocationEntity buildLocationEntity() {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setCountry("Latvia");
        locationEntity.setCity("Riga");
        locationEntity.setLoc(loc);

        return locationEntity;
    }

}