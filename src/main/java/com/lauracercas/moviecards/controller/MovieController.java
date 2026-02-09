package com.lauracercas.moviecards.controller;

import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.movie.MovieService;
import com.lauracercas.moviecards.util.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Controller
public class MovieController {

    private static final String MOVIE_LITERAL = "movie";
    private static final String TITLE_LITERAL = "title";
    private static final String MOVIE_FORM_VIEW = "movies/form";

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("movies")
    public String getMoviesList(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "movies/list";
    }

    @GetMapping("movies/new")
    public String newMovie(Model model) {
        model.addAttribute(MOVIE_LITERAL, new Movie());
        model.addAttribute(TITLE_LITERAL, Messages.NEW_MOVIE_TITLE);
        return MOVIE_FORM_VIEW;
    }

    @PostMapping("saveMovie")
    public String saveMovie(@ModelAttribute Movie movie, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return MOVIE_FORM_VIEW;
        }
        Movie movieSaved = movieService.save(movie);
        if (movieSaved.getId() != null) {
            model.addAttribute("message", Messages.UPDATED_MOVIE_SUCCESS);
        } else {
            model.addAttribute("message", Messages.SAVED_MOVIE_SUCCESS);
        }

        model.addAttribute(MOVIE_LITERAL, movieSaved);
        model.addAttribute(TITLE_LITERAL, Messages.EDIT_MOVIE_TITLE);
        return MOVIE_FORM_VIEW;
    }

    @GetMapping("editMovie/{movieId}")
    public String editMovie(@PathVariable Integer movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);
        List<Actor> actors = movie.getActors();
        model.addAttribute(MOVIE_LITERAL, movie);
        model.addAttribute("actors", actors);

        model.addAttribute(TITLE_LITERAL, Messages.EDIT_MOVIE_TITLE);

        return MOVIE_FORM_VIEW;
    }


}
