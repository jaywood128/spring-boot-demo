package com.johnathon.podcast_blast.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "password is required")
    private String passwordDigest;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "user_podcast",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "podcast_id")}
    )
    private Collection<Podcast> podcasts = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "user_episode",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "episode_id")}
    )
    private Collection<Episode> episodes = new ArrayList<>();

    public User(Long id, String name, String email, String passwordDigest) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordDigest = passwordDigest;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public Collection<Episode> getEpisodes() {
        return episodes;
    }

    public Collection<Podcast> getPodcasts() {
        return podcasts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordDigest(String passwordDigest) {
        this.passwordDigest = passwordDigest;
    }

    public void setEpisodes(Episode episode) {
        if ((episode != null) && (!this.episodes.contains(episode))) {
            this.episodes.add(episode);
        }
    }

    public void setPodcasts(Podcast podcast) {
        if ((podcast != null) && (!this.podcasts.contains(podcast))) {
            this.podcasts.add(podcast);
        }
    }


}

