package org.toufik.wiremocktut.exceptions;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(String statusText, WebClientResponseException exception) {
        super(statusText, exception);
    }
}
