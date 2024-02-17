package ru.yandex.practicum.filmorate.controller;

import java.util.*;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/users")
    public Collection<User> getAll() {
        log.info("Текущее количество пользователей: {}", service.get().size());
        return service.get();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Integer id) {
        return service.get(id);
    }

    @GetMapping("/users/{id}/friends")
    public Collection<User> getUserFriends(@PathVariable Integer id) {
        return service.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Collection<User> getUnionFriends(@PathVariable("id") Integer id, @PathVariable("otherId") Integer otherId) {
        return service.getUnionFriends(id, otherId);
    }

    @PostMapping("/users")
    public User postUser(@Valid @RequestBody User user) {
        return service.post(user);
    }

    @PutMapping("/users")
    public User putUser(@Valid @RequestBody User user) {
        return service.put(user.getId(), user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public User putFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        return service.putFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        service.deleteFriend(id, friendId);
    }
}
