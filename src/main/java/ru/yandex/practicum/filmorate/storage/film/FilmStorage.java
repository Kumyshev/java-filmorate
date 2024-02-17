package ru.yandex.practicum.filmorate.storage.film;

import java.util.Collection;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {

    Collection<Film> get();

    Film get(Integer id);

    Film post(Film film);

    Film put(Film film);
}
