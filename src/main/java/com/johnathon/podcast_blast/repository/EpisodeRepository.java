package com.johnathon.podcast_blast.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnathon.podcast_blast.model.Episode;

import java.util.Optional;

public interface EpisodeRepository extends JpaRepository <Episode, Long> {
    Optional<Episode> findById(Long id);
}

