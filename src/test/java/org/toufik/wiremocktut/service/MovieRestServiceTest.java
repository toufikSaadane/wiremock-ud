package org.toufik.wiremocktut.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.toufik.wiremocktut.dto.Movie;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MovieRestServiceTest {

    WebClient webClient;
    MovieRestService movieRestService;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:8081";
        webClient = WebClient.create(baseUrl);
        movieRestService = new MovieRestService(webClient);
    }

    @Test
    void retrieveAllMovies() {
        List<Movie> movies = movieRestService.retrieveAllMovies();
        Movie movie = movies.stream().filter(movie -> movie.getName().equals("Batman Begins"));
        assertThat(movies).contains(movie);
        assertTrue(movies.size() > 0);
        assertThat(movies.size()).isGreaterThan(0);
    }

    @Test
    void retrieveMovieById() {
        Integer id = 1;
        Movie movie = movieRestService.retrieveMovieById(id);
        assertEquals("Batman Begins", movie.getName());
        assertThat(movie.getName()).isEqualTo("Batman Begins");
    }
}