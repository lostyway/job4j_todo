package ru.job4j.todo.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;

    @Column(name = "done")
    private Boolean completed;

    @Column(updatable = false)
    private LocalDateTime created;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
    }
}
