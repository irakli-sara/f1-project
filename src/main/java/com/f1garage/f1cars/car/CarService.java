package com.f1garage.f1cars.car;

import com.f1garage.f1cars.exception.CarNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    @Transactional(readOnly = true)
    public Car findById(Long id) {
        log.info("Finding car by id: {}", id);
        return carRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Car not found with id: {}", id);
                    return new CarNotFoundException(id);
                });
    }

    @Transactional(readOnly = true)
    public Page<Car> findAll(Pageable pageable) {
        log.info("Finding all cars with pagination");
        return carRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Car> findAll() {
        log.info("Finding all cars");
        return carRepository.findAllByOrderByModelAsc();
    }

    @Transactional(readOnly = true)
    public Page<Car> search(String model, Pageable pageable) {
        log.info("Searching cars by model: {}", model);
        return carRepository.findByModelContainingIgnoreCase(model, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Car> findByTeamName(String teamName, Pageable pageable) {
        log.info("Finding cars by team name: {}", teamName);
        return carRepository.findByTeamName(teamName, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Car> findBySeason(Integer season, Pageable pageable) {
        log.info("Finding cars by season: {}", season);
        return carRepository.findBySeason(season, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Car> findBySeasonAndTeam(Integer season, String teamName, Pageable pageable) {
        log.info("Finding cars by season {} and team {}", season, teamName);
        return carRepository.findBySeasonAndTeamName(season, teamName, pageable);
    }

    @Transactional
    public Car save(Car car) {
        log.info("Saving car: {}", car.getModel());
        Car saved = carRepository.save(car);
        log.info("Car saved successfully with id: {}", saved.getId());
        return saved;
    }

    @Transactional
    public Car update(Long id, Car carDetails) {
        log.info("Updating car with id: {}", id);
        Car car = findById(id);
        car.setModel(carDetails.getModel());
        car.setHorsepower(carDetails.getHorsepower());
        car.setTopSpeed(carDetails.getTopSpeed());
        car.setSeason(carDetails.getSeason());
        car.setTeam(carDetails.getTeam());
        if (carDetails.getImageName() != null) {
            car.setImageName(carDetails.getImageName());
        }
        Car updated = carRepository.save(car);
        log.info("Car updated successfully: {}", updated.getModel());
        return updated;
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting car with id: {}", id);
        if (!carRepository.existsById(id)) {
            log.error("Cannot delete - car not found with id: {}", id);
            throw new CarNotFoundException(id);
        }
        carRepository.deleteById(id);
        log.info("Car deleted successfully: {}", id);
    }

    @Transactional(readOnly = true)
    public List<Car> findByTeamId(Long teamId) {
        log.info("Finding cars by team id: {}", teamId);
        return carRepository.findByTeamId(teamId);
    }
}
