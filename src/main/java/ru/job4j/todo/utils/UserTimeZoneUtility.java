package ru.job4j.todo.utils;

import lombok.experimental.UtilityClass;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.ZoneId;

@UtilityClass
public class UserTimeZoneUtility {

    public static Task changeTimeZone(Task task) {
        User user = task.getUser();
        ZoneId userZone = user != null
                ? ZoneId.of(user.getTimezone())
                : ZoneId.of("UTC");

        task.setCreated(task.getCreated().
                atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(userZone).toLocalDateTime());

        return task;
    }
}
