package ru.yandex.practicum.filmorate.controller;

import java.time.LocalDate;
import java.util.*;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@Slf4j
public class UserController {
    private final Map<Integer, User> map = new HashMap<>();

    @GetMapping("/users")
    public Collection<User> getAll() {
        log.info("Текущее количество пользователей: {}", map.values().size());
        return map.values();
    }

    @PostMapping("/users")
    public User postUser(@Valid @RequestBody User user) {
        if (user.getLogin().contains(" ") || user.getBirthday().isAfter(LocalDate.now()))
            throw new ValidationException();
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());
        if (user.getId() == 0)
            user.setId(idGenerated());
        map.put(user.getId(), user);
        return user;
    }

    @PutMapping("/users")
    public User putUser(@Valid @RequestBody User user) {
        if (!map.containsKey(user.getId()))
            throw new ValidationException();
        var item = map.get(user.getId());
        item.setName(user.getName());
        item.setEmail(user.getEmail());
        item.setLogin(user.getLogin());
        item.setBirthday(user.getBirthday());
        map.put(item.getId(), item);
        return item;
    }

    int idGenerated() {
        var value = map.size();
        return ++value;
    }
}
