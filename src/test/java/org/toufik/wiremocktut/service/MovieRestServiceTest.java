package org.toufik.wiremocktut.service;

import com.github.jenspiegsa.wiremockextension.ConfigureWireMock;
import com.github.jenspiegsa.wiremockextension.InjectServer;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.Options;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.reactive.function.client.WebClient;
import org.toufik.wiremocktut.dto.Movie;

import java.util.List;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(WireMockExtension.class)
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
        assertTrue(movies.size() > 0);
        assertThat(movies).isNotEmpty();
    }



    @Test
    void retrieveMovieById() {
        Integer id = 1;
        Movie movie = movieRestService.retrieveMovieById(id);
        assertEquals("Batman Begins", movie.getName());
    }
}