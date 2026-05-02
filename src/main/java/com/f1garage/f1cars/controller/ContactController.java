package com.f1garage.f1cars.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ContactController {

    @GetMapping("/contact")
    public String contact(Model model) {
        log.info("Contact page accessed");
        return "contact";
    }
}
