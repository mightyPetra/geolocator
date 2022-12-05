package com.eurofins.locator.controllers;

import com.eurofins.locator.dto.UniversityDTO;
import com.eurofins.locator.services.UniLocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UniLocatorController {

    @Autowired
    private UniLocatorService uniLocatorService;

    @GetMapping("/universities")
    public ResponseEntity<List<UniversityDTO>> getListOfUnisByIP(@RequestParam(required = false, defaultValue = "") String country) {
        return ResponseEntity.ok(uniLocatorService.getListOfUnisByCountry(country));
    }
}
