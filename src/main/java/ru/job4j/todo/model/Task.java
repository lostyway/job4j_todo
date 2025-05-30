package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String description;

    @Column(name = "done")
    private Boolean completed;

    @Column(updatable = false)
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
        if (completed == null) {
            completed = false;
        }
    }

    public Task(String description, Boolean completed) {
        this.description = description;
        this.completed = completed;
    }

    public Task(String description) {
        this.description = description;
    }
}
