package com.f1garage.f1cars.car;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Page<Car> findByModelContainingIgnoreCase(String model, Pageable pageable);

    @Query("SELECT c FROM Car c WHERE c.team.name = :teamName")
    Page<Car> findByTeamName(@Param("teamName") String teamName, Pageable pageable);

    Page<Car> findBySeason(Integer season, Pageable pageable);

    @Query("SELECT c FROM Car c WHERE c.season = :season AND c.team.name = :teamName")
    Page<Car> findBySeasonAndTeamName(@Param("season") Integer season, @Param("teamName") String teamName, Pageable pageable);

    List<Car> findAllByOrderByModelAsc();

    List<Car> findByTeamId(Long teamId);
}
