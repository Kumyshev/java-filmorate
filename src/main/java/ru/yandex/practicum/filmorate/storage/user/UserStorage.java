package ru.yandex.practicum.filmorate.storage.user;

import java.util.Collection;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {

    Collection<User> get();

    User get(Integer id);

    User post(User user);

    User put(User user);
}
