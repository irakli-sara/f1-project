package com.f1garage.f1cars.config;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributes {

    private final AppProperties appProperties;

    @ModelAttribute("appTitle")
    public String getAppTitle() {
        return appProperties.getTitle();
    }
}
