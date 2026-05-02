package com.f1garage.f1cars.car;

import com.f1garage.f1cars.config.AppProperties;
import com.f1garage.f1cars.team.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final TeamService teamService;
    private final AppProperties appProperties;

    @GetMapping("/cars")
    public String listCars(@RequestParam(required = false) String model,
                           @RequestParam(required = false) Integer season,
                           @RequestParam(required = false) String team,
                           @SortDefault(sort = "model", direction = Sort.Direction.ASC) Pageable pageable,
                           Model m) {
        log.info("Car list page accessed. Filters - model: {}, season: {}, team: {}", model, season, team);

        if (model != null && !model.isEmpty()) {
            m.addAttribute("carPage", carService.search(model, pageable));
        } else if (season != null && team != null && !team.isEmpty()) {
            m.addAttribute("carPage", carService.findBySeasonAndTeam(season, team, pageable));
        } else if (season != null) {
            m.addAttribute("carPage", carService.findBySeason(season, pageable));
        } else if (team != null && !team.isEmpty()) {
            m.addAttribute("carPage", carService.findByTeamName(team, pageable));
        } else {
            m.addAttribute("carPage", carService.findAll(pageable));
        }

        m.addAttribute("teams", teamService.findAll());
        m.addAttribute("filterModel", model);
        m.addAttribute("filterSeason", season);
        m.addAttribute("filterTeam", team);
        return "cars";
    }

    @GetMapping("/cars/add")
    public String showAddForm(Model model) {
        log.info("Car add form accessed");
        model.addAttribute("car", new Car());
        model.addAttribute("teams", teamService.findAll());
        return "car-form";
    }

    @PostMapping("/cars/save")
    public String saveCar(@Valid @ModelAttribute("car") Car car,
                          BindingResult bindingResult,
                          @RequestParam("file") MultipartFile file,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.warn("Car form has validation errors");
            model.addAttribute("teams", teamService.findAll());
            return "car-form";
        }

        try {
            if (!file.isEmpty()) {
                String filename = saveFile(file);
                car.setImageName(filename);
                log.info("Car image saved: {}", filename);
            }

            carService.save(car);
            redirectAttributes.addFlashAttribute("successMessage", "Car saved successfully!");
            log.info("Car saved via controller: {}", car.getModel());
        } catch (IOException e) {
            log.error("Error saving car image: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving car image");
        }

        return "redirect:/cars";
    }

    @GetMapping("/cars/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.info("Car edit form accessed for id: {}", id);
        Car car = carService.findById(id);
        model.addAttribute("car", car);
        model.addAttribute("teams", teamService.findAll());
        return "car-form";
    }

    @PostMapping("/cars/update/{id}")
    public String updateCar(@PathVariable Long id,
                            @Valid @ModelAttribute("car") Car car,
                            BindingResult bindingResult,
                            @RequestParam("file") MultipartFile file,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.warn("Car update form has validation errors");
            model.addAttribute("teams", teamService.findAll());
            return "car-form";
        }

        try {
            if (!file.isEmpty()) {
                String filename = saveFile(file);
                car.setImageName(filename);
                log.info("Car image updated: {}", filename);
            }

            carService.update(id, car);
            redirectAttributes.addFlashAttribute("successMessage", "Car updated successfully!");
            log.info("Car updated via controller: {}", car.getModel());
        } catch (IOException e) {
            log.error("Error updating car image: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating car image");
        }

        return "redirect:/cars";
    }

    @GetMapping("/cars/delete/{id}")
    public String deleteCar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Car delete requested for id: {}", id);
        carService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Car deleted successfully!");
        return "redirect:/cars";
    }

    private String saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
        String filename = UUID.randomUUID().toString() + extension;

        Path uploadPath = Paths.get(appProperties.getUploadDir());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }
}
