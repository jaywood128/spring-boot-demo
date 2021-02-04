package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.model.Episode;
import com.johnathon.podcast_blast.model.Podcast;
import com.johnathon.podcast_blast.model.User;
import com.johnathon.podcast_blast.repository.EpisodeRepository;
import com.johnathon.podcast_blast.repository.PodcastRepository;
import com.johnathon.podcast_blast.repository.UserRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@RestController
@RequestMapping("/api")
public class EpisodeController {
    private EpisodeRepository episodeRepository;
    private UserRepository userRepository;
    private PodcastRepository podcastRepository;
    @Autowired
    private AppSecurityConfig appSecurityConfig;
    @Autowired
    private WebClient.Builder webClientBuilder;
    private Collection<Podcast> emptyEpisodeCollection = new ArrayList<>();
    private static final String baseURL = "https://listen-api.listennotes.com/api/v2";

    public EpisodeController(EpisodeRepository episodeRepository, UserRepository userRepository, PodcastRepository podcastRepository) {
        super();
        this.episodeRepository = episodeRepository;
        this.userRepository = userRepository;
        this.podcastRepository = podcastRepository;
    }

    @PostMapping("/{id}/episodes/{episodeApiId}/{podcastApiId}")
    public ResponseEntity<?> addEpisode(@PathVariable("id") String id, @PathVariable String episodeApiId, @PathVariable String podcastApiId) {
        Optional<User> user = userRepository.findById(Long.valueOf(id));

        Optional<Episode> episodeCheck = episodeRepository.findByApiId(episodeApiId);
        Optional<Podcast> podcastCheck = podcastRepository.findByApiId(podcastApiId);
        // Check to see if a user has the episode added. The episode also needs a podcast.
        // If that podcastCheck is emtpy, it has to be created otherwise it exits and needs to be set on the
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(episodeCheck.isPresent() && user.get().getEpisodes().contains(episodeCheck.get())){
            return new ResponseEntity<Error>(HttpStatus.NOT_FOUND);
        }
        if(podcastCheck.isEmpty()){
            // Adding a new episode to a new podcast.
            Podcast newPodcast = new Podcast(podcastApiId);
            podcastRepository.save(newPodcast);
            Episode newEpisode = new Episode(episodeApiId, newPodcast);
            user.get().addEpisode(newEpisode);
            newEpisode.setUser(user.get());
            userRepository.save(user.get());
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        if (episodeCheck.isPresent() || episodeCheck.isEmpty()){
            //Adding an episode to an existing podcast.
            user.get().addEpisode(episodeCheck.get());
            episodeCheck.get().setUser(user.get());
            userRepository.save(user.get());
            episodeRepository.save(episodeCheck.get());
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        return new ResponseEntity<Error>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}/episodes", produces = "application/json")
    public ResponseEntity<List<JSONObject>> getEpisodes(@PathVariable("id") Integer id) {
        Optional<User> foundUser = userRepository.findById(Long.valueOf(id));
        System.out.println("User id is: " + id);
        List<JSONObject> returnedEpisodes = new ArrayList<>();
        if (foundUser.isEmpty()) {
            List<JSONObject> noEntities = new ArrayList<>();
            return new ResponseEntity<>(noEntities, HttpStatus.UNAUTHORIZED);
        } else {
            User user = foundUser.get();
            Set<Episode> usersEpisodes = user.getEpisodes();
            Set<String> episodeIdArrayList = new HashSet<>();
            for(Episode e : usersEpisodes){
                episodeIdArrayList.add(e.getApiId());
            }
            if (usersEpisodes != null) {
                for (String aid : episodeIdArrayList) {
                    System.out.println("api id: " + aid);
                    JSONObject jsonObject = webClientBuilder
                            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .defaultHeader("X-ListenAPI-Key", appSecurityConfig.getApiKey())
                            .build()
                            .get()
                            .uri(baseURL + "/episodes/" + aid + "?sort=recent_first")
                            .retrieve()
                            .bodyToMono(JSONObject.class)
                            .block();
                    if(jsonObject != null){
                        returnedEpisodes.add(jsonObject);
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
