package ru.yandex.practicum.filmorate.storage.film;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Film;

@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();

    int idGenerated() {
        var value = films.size();
        return ++value;
    }

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
        if (film.getId() == 0)
            film.setId(idGenerated());
        return films.put(film.getId(), film);
    }

    @Override
    public Film put(Film film) {
        return films.put(film.getId(), film);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return films.values().stream()
                .sorted(Comparator.comparingInt(film -> ((Film) film).getLike().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
