package com.eurofins.locator.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String error(HttpServletRequest request) {
        log.warn("Invalid request encountered");
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int errorCode = status.hashCode();
            if (errorCode == 404) {
                return "Nothing's here " + Character.toString(0x1F62A);
            }
        }
        return "Unknown error encountered " + Character.toString(0x1F631);
    }

}
