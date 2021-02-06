package com.johnathon.podcast_blast.controller;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.Serializable;


@RestController
@RequestMapping("/api")
public class SearchController {
    private static final String baseURL = "https://listen-api.listennotes.com/api/v2";
    @Autowired
    private  WebClient.Builder webClientBuilder;
    @Autowired
    private AppSecurityConfig appSecurityConfig;

    public SearchController() {
    }

    @GetMapping("/full-text-search/{text}")
    private ResponseEntity<? extends Serializable> fullTextSearch(@PathVariable String text){
        JSONObject jsonObject = webClientBuilder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-ListenAPI-Key", appSecurityConfig.getApiKey())
                .build()
                .get()
                .uri(baseURL + "/search?sort_by_date=1&type=episode&offset=0&len_min=10&len_max=200&published_before=1580172454000&published_after=0&only_in=title,description,author,audio&language=English&q=" + text)
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
        if (jsonObject != null) {
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        }
        return new ResponseEntity<Error>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/get-genres")
    private ResponseEntity<? extends Serializable> getGenres() {
        JSONObject jsonObject = webClientBuilder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-ListenAPI-Key", appSecurityConfig.getApiKey())
                .build()
                .get()
                .uri(baseURL + "/genres?top_level_only=1")
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
        if (jsonObject != null) {
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        }
        return new ResponseEntity<Error>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/best-podcasts-by-genre/{genre-id}")
    private ResponseEntity<? extends Serializable> getBestPodcastsByGenre(@PathVariable("genre-id") String genreid)  {
        JSONObject jsonObject = webClientBuilder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-ListenAPI-Key", appSecurityConfig.getApiKey())
                .build()
                .get()
                .uri(baseURL + "/best_podcasts?genre_id=" + genreid + "&page=<integer>&region=us&safe_mode=0")
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
        if (jsonObject != null) {
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        }
        return new ResponseEntity<Error>(HttpStatus.NOT_FOUND);
    }

}
