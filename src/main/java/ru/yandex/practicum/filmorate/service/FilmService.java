package ru.yandex.practicum.filmorate.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

@Service
public class FilmService {

    @Autowired
    InMemoryFilmStorage filmStorage;
    @Autowired
    InMemoryUserStorage userStorage;

    public Collection<Film> get() {
        return filmStorage.get();
    }

    public Film get(Integer id) {
        if (!filmStorage.get().contains(filmStorage.get(id)))
            throw new NotFoundException("id");
        return filmStorage.get(id);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.get().stream()
                .sorted(Comparator.comparingInt(film -> ((Film) film).getLike().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film post(Film film) {
        if (film.getReleaseDate().isBefore(minReleaseDate()))
            throw new ValidationException();
        if (film.getId() == 0)
            film.setId(idGenerated());
        filmStorage.put(film);
        return film;
    }

    public Film put(Integer id, Film film) {
        if (!filmStorage.get().contains(filmStorage.get(id)))
            throw new NotFoundException("id");
        if (film.getReleaseDate().isBefore(minReleaseDate()))
            throw new ValidationException();
        var _film = filmStorage.get(id);
        _film.setName(film.getName());
        _film.setDescription(film.getDescription());
        _film.setReleaseDate(film.getReleaseDate());
        _film.setDuration(film.getDuration());
        _film.setLike(film.getLike());
        filmStorage.put(_film);
        return _film;
    }

    public Film putLike(Integer id, Integer userId) {
        if (!filmStorage.get().contains(filmStorage.get(id)))
            throw new NotFoundException("id");
        if (!userStorage.get().contains(userStorage.get(userId)))
            throw new NotFoundException("userId");
        var film = filmStorage.get(id);
        film.getLike().add(userId);
        return film;
    }

    public void deleteLike(Integer id, Integer userId) {
        if (!filmStorage.get().contains(filmStorage.get(id)))
            throw new NotFoundException("id");
        var film = filmStorage.get(id);
        if (!film.getLike().contains(userId))
            throw new NotFoundException("userId");
        film.getLike().remove(userId);
    }

    static final LocalDate minReleaseDate() {
        return LocalDate.of(1895, 12, 28);
    }

    int idGenerated() {
        var value = filmStorage.get().size();
        return ++value;
    }
}
