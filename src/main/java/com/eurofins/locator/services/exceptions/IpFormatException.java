package com.eurofins.locator.services.exceptions;

public class IpFormatException extends RuntimeException {

    public IpFormatException(String ip) {
        super(String.format("Whoops! '%s' is not a valid IP address", ip));
    }
}
