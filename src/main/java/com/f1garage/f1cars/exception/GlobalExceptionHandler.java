package com.f1garage.f1cars.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CarNotFoundException.class)
    public String handleCarNotFoundException(CarNotFoundException ex, RedirectAttributes redirectAttributes) {
        log.error("Car not found: {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/cars";
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public String handleTeamNotFoundException(TeamNotFoundException ex, RedirectAttributes redirectAttributes) {
        log.error("Team not found: {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/teams";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, RedirectAttributes redirectAttributes) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred. Please try again.");
        return "redirect:/";
    }
}
