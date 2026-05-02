package com.f1garage.f1cars.rest;

import com.f1garage.f1cars.car.Car;
import com.f1garage.f1cars.car.CarService;
import com.f1garage.f1cars.dto.CarDto;
import com.f1garage.f1cars.team.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarRestController {

    private final CarService carService;
    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        log.info("REST API: Get all cars");
        List<CarDto> cars = carService.findAll().stream()
                .map(this::convertToDto)
                .toList();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        log.info("REST API: Get car by id: {}", id);
        Car car = carService.findById(id);
        return ResponseEntity.ok(convertToDto(car));
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        log.info("REST API: Create car: {}", carDto.getModel());
        Car car = convertToEntity(carDto);
        Car saved = carService.save(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id, @RequestBody CarDto carDto) {
        log.info("REST API: Update car id: {}", id);
        Car car = convertToEntity(carDto);
        Car updated = carService.update(id, car);
        return ResponseEntity.ok(convertToDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        log.info("REST API: Delete car id: {}", id);
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private CarDto convertToDto(Car car) {
        return new CarDto(
                car.getId(),
                car.getModel(),
                car.getHorsepower(),
                car.getTopSpeed(),
                car.getSeason(),
                car.getTeam() != null ? car.getTeam().getName() : null,
                car.getImageName()
        );
    }

    private Car convertToEntity(CarDto dto) {
        Car car = new Car();
        car.setId(dto.getId());
        car.setModel(dto.getModel());
        car.setHorsepower(dto.getHorsepower());
        car.setTopSpeed(dto.getTopSpeed());
        car.setSeason(dto.getSeason());
        car.setImageName(dto.getImageName());
        if (dto.getTeamName() != null) {
            car.setTeam(teamService.findByName(dto.getTeamName()));
        }
        return car;
    }
}
