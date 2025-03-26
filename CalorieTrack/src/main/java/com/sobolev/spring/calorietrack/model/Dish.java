package com.sobolev.spring.calorietrack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",unique = true,nullable = false)
    @NotBlank
    @Size(max = 200,message = "Name must be up to 200 characters long")
    private String name;

    @Positive
    @Column(name = "calories", nullable = false)
    private Integer calories;

    @DecimalMin("0.0")
    @Column(name = "proteins")
    private Double proteins;

    @DecimalMin("0.0")
    @Column(name = "fats")
    private Double fats;

    @DecimalMin("0.0")
    @Column(name = "carbohydrates")
    private Double carbohydrates;
}
