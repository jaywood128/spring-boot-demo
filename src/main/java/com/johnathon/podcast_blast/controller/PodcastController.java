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
        if (foundUser.isEmpty()) {
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

                    RestTemplate restTemplate = new RestTemplate();
                    String basePodcastsUrl = baseURL + "/podcasts/" + aid + "?sort=recent_first";
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    httpHeaders.set("X-ListenAPI-Key", appSecurityConfig.getApiKey());
                    HttpEntity<Podcast> requestEntity = new HttpEntity<>(null, httpHeaders);
                    ResponseEntity<JSONObject> response = restTemplate.exchange(basePodcastsUrl,
                            HttpMethod.GET,
                            requestEntity,
                            JSONObject.class
                    );
                    if (response.getStatusCode() == HttpStatus.OK) {
                        System.out.println("Request Successful");
                        returnedPodcasts.add(response.getBody());
                    } else {
                        System.out.println("Request Failed");
                        response.getStatusCode();
                    }
                }
            }
        }
        return new ResponseEntity<>(returnedPodcasts, HttpStatus.OK);
    }

    // Create/ add podcast to podcasts
    @PostMapping(value = "/{id}/podcasts/{id}", consumes = "application/json")
    public Collection<Podcast> savePodcast(@RequestParam String userId, @RequestParam String apiId) {

//        Podcast podcast = restTemplate
//                .getForObject(baseURL + apiId + "?sort=recent_first" + appSecurityConfig.getApiKey(), Podcast.class);
//        return podcastRepository.findAll();
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
