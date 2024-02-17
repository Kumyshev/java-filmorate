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
        if (!userStorage.get().contains(userStorage.get(id)))
            throw new NotFoundException("id");
        return userStorage.get(id);
    }

    public Collection<User> getFriends(Integer id) {
        if (!userStorage.get().contains(userStorage.get(id)))
            throw new NotFoundException("id");
        User user = userStorage.get(id);
        var list = userStorage.get().stream().filter(u -> user.getFriends().contains(u.getId()))
                .collect(Collectors.toList());
        return list;
    }

    public Collection<User> getUnionFriends(Integer id, Integer otherId) {
        if (!userStorage.get().contains(userStorage.get(id)))
            throw new NotFoundException("id");
        if (!userStorage.get().contains(userStorage.get(otherId)))
            throw new NotFoundException("otherId");
        var user = userStorage.get(id).getFriends();
        var otherUser = userStorage.get(otherId).getFriends();
        var unionFriends = new HashSet<>(user);
        unionFriends.retainAll(otherUser);
        var list = userStorage.get().stream().filter(u -> unionFriends.contains(u.getId()))
                .collect(Collectors.toList());
        return list;
    }

    public User post(User user) {
        if (user.getLogin().contains(" "))
            throw new ValidationException();
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());
        if (user.getId() == 0)
            user.setId(idGenerated());
        userStorage.post(user);
        return user;
    }

    public User put(Integer id, User user) {
        if (!userStorage.get().contains(userStorage.get(id)))
            throw new NotFoundException("id");
        var updatedUser = userStorage.get(id);
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
        if (!userStorage.get().contains(userStorage.get(id)))
            throw new NotFoundException("id");
        if (!userStorage.get().contains(userStorage.get(friendId)))
            throw new NotFoundException("friendId");
        var updatedUser = userStorage.get(id);
        var updatedUserFriend = userStorage.get(friendId);
        updatedUser.getFriends().add(friendId);
        updatedUserFriend.getFriends().add(id);
        userStorage.put(updatedUser);
        userStorage.put(updatedUserFriend);
        return updatedUser;
    }

    public void deleteFriend(Integer id, Integer friendId) {
        if (!userStorage.get().contains(userStorage.get(id)))
            throw new NotFoundException("id");
        if (!userStorage.get().contains(userStorage.get(friendId)))
            throw new NotFoundException("friendId");
        var updatedUser = userStorage.get(id);
        var updatedUserFriend = userStorage.get(friendId);
        updatedUser.getFriends().remove(friendId);
        updatedUserFriend.getFriends().remove(id);
        userStorage.put(updatedUser);
        userStorage.put(updatedUserFriend);
    }

    int idGenerated() {
        var value = userStorage.get().size();
        return ++value;
    }
}
