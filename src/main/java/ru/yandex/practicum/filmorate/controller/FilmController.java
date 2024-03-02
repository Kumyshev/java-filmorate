package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@Slf4j
public class FilmController {

    @Autowired
    FilmService service;

    @GetMapping("/films")
    public Collection<Film> getAll() {
        var films = service.get();
        log.info("Текущее количество фильмов: {}", films.size());
        return films;
    }

    @GetMapping("/films/{id}")
    public Film get(@PathVariable("id") Integer id) {
        return service.get(id);
    }

    @GetMapping("/films/popular")
    public Collection<Film> getPopularFilms(
            @RequestParam(value = "count", defaultValue = "10") Integer count) {
        return service.getPopularFilms(count);
    }

    @PostMapping("/films")
    public Film postFilm(@Valid @RequestBody Film film) {
        service.post(film);
        return film;
    }

    @PutMapping("/films")
    public Film putFilm(@Valid @RequestBody Film film) {
        service.put(film);
        return film;
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film putLike(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        return service.putLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        service.deleteLike(id, userId);
    }
}
