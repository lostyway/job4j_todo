package ru.job4j.todo.utils;

import lombok.experimental.UtilityClass;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.ZoneId;

@UtilityClass
public class UserTimeZoneUtility {

    public Task changeTimeZone(Task task) {
        ZoneId userZone;
        try {
            User user = task.getUser();
            userZone = user != null
                    ? ZoneId.of(user.getTimezone())
                    : ZoneId.of("UTC");
        } catch (Exception e) {
            userZone = ZoneId.of("UTC");
        }
        task.setCreated(task.getCreated().
                atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(userZone).toLocalDateTime());
        return task;
    }
}
