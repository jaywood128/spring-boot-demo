package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.model.Episode;
import com.johnathon.podcast_blast.repository.EpisodeRepository;
import com.johnathon.podcast_blast.repository.EpisodeRepository;
import com.johnathon.podcast_blast.model.Episode;
import com.johnathon.podcast_blast.repository.EpisodeRepository;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class EpisodeController {
    private EpisodeRepository episodeRepository;

    public EpisodeController(EpisodeRepository episodeRepository){
        super();
        this.episodeRepository = episodeRepository;
    }
    @PostMapping("/episodes")
    public Episode saveEpisode(Episode episode){
        return episodeRepository.save(episode);
    }
    @GetMapping("/episodes")
    public Collection<Episode> getEpisodes(){
        return episodeRepository.findAll();
    }
    @GetMapping("/episodes/{id}")
    public Optional<Episode> getEpisode(@PathVariable String id){
        Long longId = Long.parseLong(id);
        return episodeRepository.findById(longId);
    }
}
