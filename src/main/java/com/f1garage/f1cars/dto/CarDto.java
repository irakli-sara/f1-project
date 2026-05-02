package com.f1garage.f1cars.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

    private Long id;
    private String model;
    private Integer horsepower;
    private Integer topSpeed;
    private Integer season;
    private String teamName;
    private String imageName;
}
