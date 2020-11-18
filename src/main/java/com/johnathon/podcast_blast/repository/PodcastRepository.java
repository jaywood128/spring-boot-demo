package com.johnathon.podcast_blast.repository;

import com.johnathon.podcast_blast.model.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PodcastRepository extends JpaRepository<Podcast, Long> {
    Podcast findByTitleOriginal(String title);
}