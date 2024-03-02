package ru.yandex.practicum.filmorate.storage.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.User;

@Component
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public Collection<User> get() {
        return users.values();
    }

    @Override
    public User get(Integer id) {
        return users.get(id);
    }

    @Override
    public User post(User user) {
        if (user.getId() == 0)
            user.setId(idGenerated());
        return users.put(user.getId(), user);
    }

    @Override
    public User put(User user) {
        return users.put(user.getId(), user);
    }

    int idGenerated() {
        var value = users.size();
        return ++value;
    }
}
