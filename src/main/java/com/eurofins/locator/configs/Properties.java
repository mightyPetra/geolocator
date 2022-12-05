package com.eurofins.locator.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties()
public class Properties {

    @Value("${api.url.ipinfo}")
    private String ipinfo;

    @Value("${api.url.unis}")
    private String unis;

    public String getIpApiUrl() {
        return ipinfo;
    }

    public String getUnisApiUrl() {
        return unis;
    }
}
