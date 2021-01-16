package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.model.Episode;
import com.johnathon.podcast_blast.model.Podcast;
import com.johnathon.podcast_blast.model.User;
import com.johnathon.podcast_blast.repository.EpisodeRepository;
import com.johnathon.podcast_blast.repository.UserRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api")
public class EpisodeController {
    private EpisodeRepository episodeRepository;
    private UserRepository userRepository;
    @Autowired
    private AppSecurityConfig appSecurityConfig;
    @Autowired
    private RestTemplate restTemplate;
    private Collection<Podcast> emptyEpisodeCollection = new ArrayList<>();
    private static final String baseURL = "https://listen-api.listennotes.com/api/v2";

    public EpisodeController(EpisodeRepository episodeRepository, UserRepository userRepository) {
        super();
        this.episodeRepository = episodeRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/{id}/episodes")
    public Episode saveEpisode(@PathVariable("id") String id, Episode episode) {
        return episodeRepository.save(episode);
    }

    @GetMapping(value = "/{id}/episodes", produces = "application/json")
    public ResponseEntity<List<JSONObject>> getEpisodes(@PathVariable("id") Integer id) {
        Optional<User> foundUser = userRepository.findById(Long.valueOf(id));
        System.out.println("User id is: " + id);
        List<JSONObject> returnedEpisodes = new ArrayList<>();
        if (foundUser.isEmpty()) {
            List<JSONObject> noEntities = new ArrayList<>();
            return new ResponseEntity<>(noEntities, HttpStatus.NOT_FOUND);
        } else {
            User user = foundUser.get();
            Collection<Episode> usersPodcasts = user.getEpisodes();
            List<String> episodeIdArrayList = new ArrayList<>();
            if (usersPodcasts != null) {
                for (Episode episode : usersPodcasts) {
                    String apiId = episode.getApiId();
                    episodeIdArrayList.add(apiId);
                    System.out.println("Episode id arraylist size is: " + episodeIdArrayList.size());
                }
                for (String aid : episodeIdArrayList) {
                    System.out.println("api id: " + aid);

                    RestTemplate restTemplate = new RestTemplate();
                    String basePodcastsUrl = baseURL + "/episodes/" + aid;
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
                        returnedEpisodes.add(response.getBody());
                    } else {
                        System.out.println("Request Failed");
                        response.getStatusCode();
                    }
                }
            }
        }
        return new ResponseEntity<>(returnedEpisodes, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public Optional<Episode> getEpisode(@PathVariable String id) {
        Long longId = Long.parseLong(id);
        return episodeRepository.findById(longId);
    }
}
