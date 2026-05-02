package com.f1garage.f1cars.team;

import com.f1garage.f1cars.car.Car;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Team name is required")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Country is required")
    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "principal")
    private String principal;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Car> cars = new ArrayList<>();
}
