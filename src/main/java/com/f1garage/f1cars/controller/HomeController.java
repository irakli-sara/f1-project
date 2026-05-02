package com.f1garage.f1cars.controller;

import com.f1garage.f1cars.car.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CarService carService;

    @GetMapping({"/", "/index", "/home"})
    public String home(Model model) {
        log.info("Home page accessed");
        model.addAttribute("recentCars", carService.findAll().stream().limit(6).toList());
        return "index";
    }
}
