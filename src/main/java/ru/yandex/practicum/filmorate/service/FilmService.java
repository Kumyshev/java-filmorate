package ru.yandex.practicum.filmorate.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
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
        Film film = filmStorage.get(id);
        if (film == null)
            throw new NotFoundException("filmId");
        return film;
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.getPopularFilms(count);
    }

    public Film post(Film film) {
        if (film.getReleaseDate().isBefore(minReleaseDate()))
            throw new ValidationException();
        filmStorage.post(film);
        return film;
    }

    public Film put(Film film) {
        if (film == null)
            throw new NotFoundException("filmId");
        if (film.getReleaseDate().isBefore(minReleaseDate()))
            throw new ValidationException();
        var updatedFilm = filmStorage.get(film.getId());
        updatedFilm.setName(film.getName());
        updatedFilm.setDescription(film.getDescription());
        updatedFilm.setReleaseDate(film.getReleaseDate());
        updatedFilm.setDuration(film.getDuration());
        updatedFilm.setLike(film.getLike());
        filmStorage.put(updatedFilm);
        return updatedFilm;
    }

    public Film putLike(Integer id, Integer userId) {
        Film film = filmStorage.get(id);
        if (film == null)
            throw new NotFoundException("filmId");
        User user = userStorage.get(userId);
        if (user == null)
            throw new NotFoundException("userId");
        film.getLike().add(user.getId());
        return film;
    }

    public void deleteLike(Integer id, Integer userId) {
        Film film = filmStorage.get(id);
        if (film == null)
            throw new NotFoundException("filmId");
        User user = userStorage.get(userId);
        if (user == null)
            throw new NotFoundException("userId");
        film.getLike().remove(user.getId());
    }

    static final LocalDate minReleaseDate() {
        return LocalDate.of(1895, 12, 28);
    }
}
