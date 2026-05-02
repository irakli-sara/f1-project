package com.f1garage.f1cars.exception;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(Long id) {
        super("Team not found with id: " + id);
    }
}
