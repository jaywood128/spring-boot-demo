package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.model.Podcast;
import com.johnathon.podcast_blast.repository.PodcastRepository;
import com.johnathon.podcast_blast.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
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
    @GetMapping("/podcast/${id}")
        public Optional<Podcast> getPodcast(@PathParam("id") Long id){
            return podcastRepository.findById(id);
    }

}
