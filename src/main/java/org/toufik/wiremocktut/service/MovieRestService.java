package org.toufik.wiremocktut.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.toufik.wiremocktut.constants.MovieAppConstants;
import org.toufik.wiremocktut.dto.Movie;
import org.toufik.wiremocktut.exceptions.MovieNotFoundException;

import java.util.List;


@Slf4j
public class MovieRestService {

    private final WebClient webClient;

    public MovieRestService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Movie> retrieveAllMovies() {
        return webClient.get()
                .uri(MovieAppConstants.GET_ALL_MOVIES_V1)
                .retrieve()
                .bodyToFlux(Movie.class)
                .collectList()
                .block();
    }

    public Movie retrieveMovieById(Integer id) {
        log.info("start");
        try {
            return webClient.get()
                    .uri(MovieAppConstants.GET_ALL_MOVIES_V1_BY_ID, id)
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
        }catch (WebClientResponseException responseException){
            log.error("movie with id : {} not found", id);
            throw new MovieNotFoundException(responseException.getStatusText(), responseException);
        }catch (Exception exception){
            log.error("movie with id : {} not found", id);
            throw exception;
        }
    }
}
