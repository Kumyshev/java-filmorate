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
        log.info("Текущее количество фильмов: {}", service.get().size());
        return service.get();
    }

    @GetMapping("/films/{id}")
    public Film get(@PathVariable("id") Integer id) {
        return service.get(id);
    }

    @GetMapping("/films/popular")
    public Collection<Film> getPopularFilms(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        return service.getPopularFilms(count);
    }

    @PostMapping("/films")
    public Film postFilm(@Valid @RequestBody Film film) {
        service.post(film);
        return film;
    }

    @PutMapping("/films")
    public Film putFilm(@Valid @RequestBody Film film) {
        service.put(film.getId(), film);
        return film;
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film putFriend(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        return service.putLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteFriend(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        service.deleteLike(id, userId);
    }
}
