package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@Slf4j
public class FilmController {
    private final Map<Integer, Film> map = new HashMap<>();

    @GetMapping("/films")
    public Collection<Film> getAll() {
        log.info("Текущее количество фильмов: {}", map.values().size());
        return map.values();
    }

    @PostMapping("/films")
    public Film postFilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(minReleaseDate()))
            throw new ValidationException();
        if (film.getId() == 0)
            film.setId(idGenerated());
        map.put(film.getId(), film);
        return film;
    }

    @PutMapping("/films")
    public Film putFilm(@Valid @RequestBody Film film) {
        if (!map.containsKey(film.getId()) || film.getReleaseDate().isBefore(minReleaseDate()))
            throw new ValidationException();
        var item = map.get(film.getId());
        item.setName(film.getName());
        item.setDescription(film.getDescription());
        item.setReleaseDate(film.getReleaseDate());
        item.setDuration(film.getDuration());
        map.put(item.getId(), item);
        return item;
    }

    static final LocalDate minReleaseDate() {
        return LocalDate.of(1895, 12, 28);
    }

    int idGenerated() {
        var value = map.size();
        return ++value;
    }
}
