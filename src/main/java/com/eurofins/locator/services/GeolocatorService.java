package com.eurofins.locator.services;

import com.eurofins.locator.configs.Properties;
import com.eurofins.locator.dto.IpLocationDTO;
import com.eurofins.locator.persistance.enities.IpEntity;
import com.eurofins.locator.persistance.enities.LocationEntity;
import com.eurofins.locator.persistance.repositories.IpRepository;
import com.eurofins.locator.persistance.repositories.LocationRepository;
import com.eurofins.locator.services.exceptions.IpFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class GeolocatorService {

    private final IpRepository ipRepository;
    private final LocationRepository locationRepository;
    private final RestTemplate restTemplate;
    private final String ipApiUrl;

    @Autowired
    public GeolocatorService(IpRepository ipRepository,
                             LocationRepository locationRepository, RestTemplate restTemplate,
                             Properties properties) {
        this.ipRepository = ipRepository;
        this.locationRepository = locationRepository;
        this.restTemplate = restTemplate;
        this.ipApiUrl = properties.getIpApiUrl();
    }

    /**
     * get Location information by ip address String from "https://ipinfo.io/"
     * and save it to DB
     *
     * @param ip - ip address
     */

    public IpLocationDTO getLocationByIp(String ip) throws IpFormatException {
        String requestString = String.format("%s%s/geo", ipApiUrl, ip);
        try {
            var responseEntity = restTemplate.getForEntity(requestString, IpLocationDTO.class);
            IpLocationDTO locationData = Objects.requireNonNull(responseEntity.getBody());
            saveIPLocationData(locationData);
            log.info("Returning location for IP '{}'", ip);
            return locationData;
        } catch (HttpClientErrorException.NotFound ex) {
            log.error(ex.getMessage());
            throw new IpFormatException(ip);
        }
    }

    private void saveIPLocationData(IpLocationDTO ipLocationDTO) {
        String ip = ipLocationDTO.getIp();
        if (!ipRepository.existsByIp(ip)) {
            log.info("Saving IP address '{}'", ip);
            saveIpLocation(ipLocationDTO);
        } else {
            log.info("IP '{}' already exists in the DB", ip);
        }
    }

    private void saveIpLocation(IpLocationDTO ipLocationDTO) {
        IpEntity ipEntity = convertDtoToEntity(ipLocationDTO);
        ipRepository.save(ipEntity);
    }

    private LocationEntity getLocationEntity(LocationEntity locationEntity) {
        Optional<LocationEntity> foundLocation = locationRepository.findByLoc(locationEntity.getLoc());
        return foundLocation.isEmpty() ? locationRepository.save(locationEntity) : foundLocation.get();
    }

    private IpEntity convertDtoToEntity(IpLocationDTO ipLocationDTO) {
        IpEntity ipEntity = new IpEntity();
        ipEntity.setIp(ipLocationDTO.getIp());

        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setCountry(ipLocationDTO.getCountry());
        locationEntity.setCity(ipLocationDTO.getCity());
        locationEntity.setLoc(ipLocationDTO.getLoc());

        locationEntity = getLocationEntity(locationEntity);
        ipEntity.setLocationEntity(locationEntity);
        return ipEntity;
    }
}
