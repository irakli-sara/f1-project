package com.f1garage.f1cars.exception;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(Long id) {
        super("Car not found with id: " + id);
    }
}
