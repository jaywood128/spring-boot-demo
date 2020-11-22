package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.model.Episode;
import com.johnathon.podcast_blast.repository.EpisodeRepository;
import com.johnathon.podcast_blast.repository.EpisodeRepository;
import com.johnathon.podcast_blast.model.Episode;
import com.johnathon.podcast_blast.repository.EpisodeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
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
    @GetMapping("/episode/${id}")
    public Optional<Episode> getEpisode(@PathParam("id") Long id){
        return episodeRepository.findById(id);
    }
}
