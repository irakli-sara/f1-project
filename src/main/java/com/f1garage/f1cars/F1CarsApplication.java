package com.f1garage.f1cars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class F1CarsApplication {

    public static void main(String[] args) {
        SpringApplication.run(F1CarsApplication.class, args);
        log.info("F1 Garage application started successfully!");
    }
}
