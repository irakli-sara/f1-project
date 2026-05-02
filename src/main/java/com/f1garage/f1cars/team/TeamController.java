package com.f1garage.f1cars.team;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/teams")
    public String listTeams(Model model) {
        log.info("Team list page accessed");
        model.addAttribute("teams", teamService.findAll());
        return "teams";
    }

    @GetMapping("/teams/add")
    public String showAddForm(Model model) {
        log.info("Team add form accessed");
        model.addAttribute("team", new Team());
        return "team-form";
    }

    @PostMapping("/teams/save")
    public String saveTeam(@Valid @ModelAttribute("team") Team team,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.warn("Team form has validation errors");
            return "team-form";
        }

        teamService.save(team);
        redirectAttributes.addFlashAttribute("successMessage", "Team saved successfully!");
        log.info("Team saved via controller: {}", team.getName());
        return "redirect:/teams";
    }

    @GetMapping("/teams/delete/{id}")
    public String deleteTeam(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Team delete requested for id: {}", id);
        teamService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Team deleted successfully!");
        return "redirect:/teams";
    }
}
