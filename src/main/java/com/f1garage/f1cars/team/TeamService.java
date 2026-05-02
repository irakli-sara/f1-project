package com.f1garage.f1cars.team;

import com.f1garage.f1cars.exception.TeamNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional(readOnly = true)
    public Team findById(Long id) {
        log.info("Finding team by id: {}", id);
        return teamRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Team not found with id: {}", id);
                    return new TeamNotFoundException(id);
                });
    }

    @Transactional(readOnly = true)
    public List<Team> findAll() {
        log.info("Finding all teams");
        return teamRepository.findAllByOrderByNameAsc();
    }

    @Transactional
    public Team save(Team team) {
        log.info("Saving team: {}", team.getName());
        Team saved = teamRepository.save(team);
        log.info("Team saved successfully with id: {}", saved.getId());
        return saved;
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting team with id: {}", id);
        if (!teamRepository.existsById(id)) {
            log.error("Cannot delete - team not found with id: {}", id);
            throw new TeamNotFoundException(id);
        }
        teamRepository.deleteById(id);
        log.info("Team deleted successfully: {}", id);
    }

    @Transactional(readOnly = true)
    public Team findByName(String name) {
        log.info("Finding team by name: {}", name);
        return teamRepository.findByName(name)
                .orElseThrow(() -> new TeamNotFoundException(-1L));
    }
}
