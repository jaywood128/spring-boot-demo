package com.johnathon.podcast_blast.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "podcast")

public class Podcast {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiId;

    @ManyToMany(mappedBy = "podcasts")
    private Collection<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "podcast")
    private Collection<Episode> episodes = new ArrayList<>();

    public Podcast(String apiId) {
        this.apiId = apiId;
    }
    public Podcast(){ }

    public Long getId() {
        return id;
    }

    public String getApiId() {
        return apiId;
    }

    public Collection<User> getUsers() {
        return this.users;
    }

    public Collection<Episode> getEpisodes() {
        return episodes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public boolean setUser(User user) {
        if (user != null) {
            return this.users.add(user);
        }
        return false;
    }

    public void setEpisodes(Episode episode) {
        if (episode != null) {
            this.episodes.add(episode);
        }
    }


}

