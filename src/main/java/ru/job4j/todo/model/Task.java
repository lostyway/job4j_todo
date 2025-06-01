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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
        if (completed == null) {
            completed = false;
        }
    }

    public Task(int id, String description, Boolean completed, LocalDateTime created) {
        this.description = description;
        this.completed = completed;
    }

    public Task(String description) {
        this.description = description;
    }
}
