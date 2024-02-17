package ru.yandex.practicum.filmorate.storage.film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Film;

@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Collection<Film> get() {
        return films.values();
    }

    @Override
    public Film get(Integer id) {
        return films.get(id);
    }

    @Override
    public Film post(Film film) {
        return films.put(film.getId(), film);
    }

    @Override
    public Film put(Film film) {
        return films.put(film.getId(), film);
    }

}
