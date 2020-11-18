package com.johnathon.podcast_blast.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnathon.podcast_blast.model.Episode;

public interface EpisodeRepository extends JpaRepository <Episode, Long> {
    Episode findByTitleOriginal(String OrignialTitle);
}

