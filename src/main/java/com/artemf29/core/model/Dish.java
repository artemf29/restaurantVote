package com.artemf29.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "rest_unique_dish_name_idx")})
public class Dish extends AbstractBaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @NotNull
    private String name;

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 1, max = 1000000)
    private int price;

    @Column(name = "description", nullable = false, columnDefinition = "No description")
    @NotBlank
    @Size(min = 2, max = 120)
    private String description = "No description";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, String name, int price, String description) {
        super(id);
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Dish(Integer id, String name, int price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name=" + name + '\'' +
                ", price='" + price +
                ", description='" + description +
                '}';
    }
}
