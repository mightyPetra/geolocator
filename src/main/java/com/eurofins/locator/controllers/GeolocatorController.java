package com.eurofins.locator.controllers;

import com.eurofins.locator.dto.IpLocationDTO;
import com.eurofins.locator.services.GeolocatorService;
import com.eurofins.locator.services.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
@Slf4j
public class GeolocatorController {

    @Autowired
    private GeolocatorService geolocatorService;

    @GetMapping("/{ip}")
    public ResponseEntity<IpLocationDTO> getLocationByIp(@PathVariable("ip") String ip) {
        return ResponseEntity.ok(geolocatorService.getLocationByIp(ip));
    }

    @ExceptionHandler(IpFormatException.class)
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
    public ResponseEntity<String> handleIPFormatException(IpFormatException ex){
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
                .body(ex.getMessage());
    }
}