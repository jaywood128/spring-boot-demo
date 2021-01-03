package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.model.Podcast;
import com.johnathon.podcast_blast.model.User;
import com.johnathon.podcast_blast.repository.PodcastRepository;
import com.johnathon.podcast_blast.repository.UserRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLOutput;
import java.util.*;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/podcasts")
public class PodcastController {

    private PodcastRepository podcastRepository;
    private UserRepository userRepository;

    @Autowired
    private AppSecurityConfig appSecurityConfig;

    @Autowired
    private RestTemplate restTemplate;
    private Collection<Podcast> emptyPodcastCollection = new ArrayList<>();
    private static final String baseURL = "https://listen-api.listennotes.com/api/v2";

    @Autowired
    private WebClient.Builder webClientBuilder;

    public PodcastController(PodcastRepository podcastRepository, UserRepository userRepository) {
        super();
        this.podcastRepository = podcastRepository;
        this.userRepository = userRepository;
    }

    // Read a user's podcasts
    @GetMapping(value = "/{id}/podcasts", produces = "application/json")
    public ResponseEntity<List<JSONObject>> getPodcasts(@PathVariable("id") Integer userId) {
        Optional<User> foundUser = userRepository.findById(Long.valueOf(userId));
        List<JSONObject> returnedPodcasts = new ArrayList<>();
        if (!foundUser.isPresent()) {
            List<JSONObject> noEntities = new ArrayList<>();
            return new ResponseEntity<>(noEntities, HttpStatus.NOT_FOUND);
        } else {
            User user = foundUser.get();
            Collection<Podcast> usersPodcasts = user.getPodcasts();
            List<String> podcastIdArrayList = new ArrayList<>();
            if (usersPodcasts != null) {
                for (Podcast podcast : usersPodcasts) {
                    String apiId = podcast.getApiId();
                    podcastIdArrayList.add(apiId);
                    System.out.println("Podcast id arraylist size is: " + podcastIdArrayList.size());
                }
                for (String aid : podcastIdArrayList) {
                    System.out.println("api id: " + aid);

                    JSONObject jsonObject = webClientBuilder
                            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .defaultHeader("X-ListenAPI-Key", appSecurityConfig.getApiKey())
                            .build()
                            .get()
                            .uri(baseURL + "/podcasts/" + aid + "?sort=recent_first")
                            .retrieve()
                            .bodyToMono(JSONObject.class)
                            .block();
                    returnedPodcasts.add(jsonObject);
                }
            }
        }
        return new ResponseEntity<>(returnedPodcasts, HttpStatus.OK);
    }

    // Add a podcast to user's podcasts
    @PostMapping(value = "/{id}/podcasts/{apiId}", produces = "application/json", consumes = "application/json")
    public HttpStatus addPodcast(@PathVariable String id, @PathVariable String apiId) {
        Optional<User> user = userRepository.findById(Long.valueOf(id));
        if (user.isPresent()) {
            User foundUser = user.get();
            for (Podcast podcast : foundUser.getPodcasts()) {
                if (podcast.getApiId() == apiId) {
                    return HttpStatus.UNPROCESSABLE_ENTITY;
                }
            }
            Podcast newPodcast = new Podcast(apiId);
            podcastRepository.save(newPodcast);
            foundUser.addPodcast(newPodcast);
            userRepository.save(foundUser);
            System.out.println("Podcast with id " + newPodcast.getId() + "was added to " + user.get().getName() + " collection");
            for(Podcast podcast : foundUser.getPodcasts()){
                System.out.println("User's current podcasts: " + podcast.getApiId());
            }
            return HttpStatus.CREATED;
        }
        return null;
    }

    // Delete podcasts from library
    @DeleteMapping("/{id}/podcast/{id}")
    public boolean getPodcast(@RequestParam String id) {
        Long longId = Long.parseLong(id);
        Optional<Podcast> podcastDelete = podcastRepository.findById(longId);

        if (podcastDelete.isPresent()) {
            Podcast podcastExits = podcastDelete.get();
            podcastRepository.delete(podcastExits);
            return true;
        }
        return false;
    }

}
