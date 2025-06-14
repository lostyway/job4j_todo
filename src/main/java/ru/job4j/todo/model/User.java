package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "todo_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String login;
    private String password;

    @Column(name = "timezone")
    private String timezone = "UTC";

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User(String name, String login, String password, String timezone) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.timezone = timezone;
    }
}
