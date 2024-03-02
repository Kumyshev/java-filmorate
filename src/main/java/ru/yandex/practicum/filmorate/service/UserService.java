package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

@Service
public class UserService {

    @Autowired
    InMemoryUserStorage userStorage;

    public Collection<User> get() {
        return userStorage.get();
    }

    public User get(Integer id) {
        User user = userStorage.get(id);
        if (user == null)
            throw new NotFoundException("userId");
        return userStorage.get(id);
    }

    public Collection<User> getFriends(Integer id) {
        User user = userStorage.get(id);
        if (user == null)
            throw new NotFoundException("userId");
        var list = userStorage.get().stream().filter(u -> user.getFriends().contains(u.getId()))
                .collect(Collectors.toList());
        return list;
    }

    public Collection<User> getUnionFriends(Integer id, Integer otherId) {
        User user = userStorage.get(id);
        if (user == null)
            throw new NotFoundException("userId");
        User other = userStorage.get(otherId);
        if (other == null)
            throw new NotFoundException("otherId");
        var userFriends = user.getFriends();
        var otherFriends = other.getFriends();
        var unionFriends = new HashSet<>(userFriends);
        unionFriends.retainAll(otherFriends);
        var list = userStorage.get().stream().filter(u -> unionFriends.contains(u.getId()))
                .collect(Collectors.toList());
        return list;
    }

    public User post(User user) {
        if (user.getLogin().contains(" "))
            throw new ValidationException();
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());
        userStorage.post(user);
        return user;
    }

    public User put(User user) {
        var updatedUser = userStorage.get(user.getId());
        if (updatedUser == null)
            throw new NotFoundException("userId");
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());
        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setLogin(user.getLogin());
        updatedUser.setBirthday(user.getBirthday());
        updatedUser.setFriends(user.getFriends());
        userStorage.put(updatedUser);
        return user;
    }

    public User putFriend(Integer id, Integer friendId) {
        User user = userStorage.get(id);
        if (user == null)
            throw new NotFoundException("userId");
        User friend = userStorage.get(friendId);
        if (friend == null)
            throw new NotFoundException("friendId");
        user.getFriends().add(friendId);
        friend.getFriends().add(id);
        userStorage.put(user);
        userStorage.put(friend);
        return user;
    }

    public void deleteFriend(Integer id, Integer friendId) {
        User user = userStorage.get(id);
        if (user == null)
            throw new NotFoundException("userId");
        User friend = userStorage.get(friendId);
        if (friend == null)
            throw new NotFoundException("friendId");
        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
        userStorage.put(user);
        userStorage.put(friend);
    }
}
