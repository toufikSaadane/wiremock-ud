package org.toufik.wiremocktut.service.inrtegration;

import com.github.jenspiegsa.wiremockextension.ConfigureWireMock;
import com.github.jenspiegsa.wiremockextension.InjectServer;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.Options;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.toufik.wiremocktut.dto.Movie;
import org.toufik.wiremocktut.service.MovieRestService;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(WireMockExtension.class)
public class MovieIntegrationTest {

  WebClient webClient;
  MovieRestService movieRestService;

  @InjectServer
  WireMockServer wireMockServer;

  @ConfigureWireMock
  Options options = wireMockConfig()
      .port(8088)
      .notifier(new ConsoleNotifier(true));

  @BeforeEach
  void setUp() {
    int port = wireMockServer.port();
    String baseUrl = String.format("http://localhost:%s", port);
    System.out.println("========================================   " + baseUrl);
    webClient = WebClient.create(baseUrl);
    movieRestService = new MovieRestService(webClient);
  }

  @Test
  void getAll() {
    //when
    stubFor(
        get(anyUrl())
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withBodyFile("all-movies.json")
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
    );

    //given
    List<Movie> movies = movieRestService.retrieveAllMovies();
    assertTrue(movies.size() > 0);

    //then
    assertThat(movies).isNotEmpty();
  }

}
