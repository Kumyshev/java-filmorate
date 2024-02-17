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
        var _user = userStorage.get(id);
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());
        _user.setName(user.getName());
        _user.setEmail(user.getEmail());
        _user.setLogin(user.getLogin());
        _user.setBirthday(user.getBirthday());
        _user.setFriends(user.getFriends());
        userStorage.put(_user);
        return user;
    }

    public User putFriend(Integer id, Integer friendId) {
        if (!userStorage.get().contains(userStorage.get(id)))
            throw new NotFoundException("id");
        if (!userStorage.get().contains(userStorage.get(friendId)))
            throw new NotFoundException("friendId");
        var _user = userStorage.get(id);
        var _userFriend = userStorage.get(friendId);
        _user.getFriends().add(friendId);
        _userFriend.getFriends().add(id);
        userStorage.put(_user);
        userStorage.put(_userFriend);
        return _user;
    }

    public void deleteFriend(Integer id, Integer friendId) {
        if (!userStorage.get().contains(userStorage.get(id)))
            throw new NotFoundException("id");
        if (!userStorage.get().contains(userStorage.get(friendId)))
            throw new NotFoundException("friendId");
        var _user = userStorage.get(id);
        var _userFriend = userStorage.get(friendId);
        _user.getFriends().remove(friendId);
        _userFriend.getFriends().remove(id);
        userStorage.put(_user);
        userStorage.put(_userFriend);
    }

    int idGenerated() {
        var value = userStorage.get().size();
        return ++value;
    }
}
