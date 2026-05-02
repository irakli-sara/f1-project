package com.f1garage.f1cars.car;

import com.f1garage.f1cars.team.Team;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Model name is required")
    @Size(min = 2, message = "Model name must be at least 2 characters")
    @Column(name = "model", nullable = false)
    private String model;

    @Positive(message = "Horsepower must be positive")
    @Column(name = "horsepower")
    private Integer horsepower;

    @Column(name = "top_speed")
    private Integer topSpeed;

    @NotNull(message = "Season is required")
    @Column(name = "season", nullable = false)
    private Integer season;

    @Column(name = "image_name")
    private String imageName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
