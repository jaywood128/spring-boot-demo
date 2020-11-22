package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.model.Podcast;
import com.johnathon.podcast_blast.repository.PodcastRepository;
import com.johnathon.podcast_blast.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PodcastController {
    private PodcastRepository podcastRepository;

    public PodcastController(PodcastRepository podcastRepository){
        super();
        this.podcastRepository = podcastRepository;
    }
    @PostMapping("/podcasts")
        public Podcast savePodcast(Podcast podcast){
            return podcastRepository.save(podcast);
    }
    @GetMapping("/podcasts")
        public Collection<Podcast> getPodcasts(){
            return podcastRepository.findAll();
        }
    @GetMapping("/podcast/{id}")
        public Optional<Podcast> getPodcast(@PathVariable String id){
            Long longId = Long.parseLong(id);
        return podcastRepository.findById(longId);
    }

}
