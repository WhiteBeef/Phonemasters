package ru.phonemasters.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Office {

    @Id
    private Long id;
    private String address;
    private Long number;

    @ManyToMany
    private List<User> employers;
}
