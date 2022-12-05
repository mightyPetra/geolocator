package com.eurofins.locator.controllers;

import com.eurofins.locator.dto.IpLocationDTO;
import com.eurofins.locator.persistance.repositories.IpRepository;
import com.eurofins.locator.persistance.repositories.LocationRepository;
import com.eurofins.locator.services.GeolocatorService;
import com.eurofins.locator.services.exceptions.IpFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GeolocatorController.class)
class GeolocatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeolocatorService geolocatorService;
    @MockBean
    private IpRepository ipRepository;
    @MockBean
    private LocationRepository locationRepository;

    private IpLocationDTO ipLocationDTO;

    @BeforeEach
    void setUp() {
        ipLocationDTO = new IpLocationDTO();
        ipLocationDTO.setCountry("Narnia");
    }

    @Test
    void getLocationByIp() throws Exception {

        given(geolocatorService.getLocationByIp("1.1.1.1"))
                .willReturn(ipLocationDTO);

        mockMvc.perform(
                        get("/api/location/1.1.1.1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("country", is(ipLocationDTO.getCountry())));
    }

    @Test
    void getLocationByInvalidIp() throws Exception {
        given(geolocatorService.getLocationByIp("1")).willThrow(IpFormatException.class);

        mockMvc.perform(
                        get("/api/location/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isIAmATeapot());
    }

    @Test
    void getLocationByEmptyIp() throws Exception {
        given(geolocatorService.getLocationByIp(null)).willThrow(IpFormatException.class);

        mockMvc.perform(
                        get("/api/location/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }
}