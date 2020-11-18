package com.johnathon.podcast_blast.model;


import lombok.Data;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "podcast")

public class Podcast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String apiId;

    @ManyToMany(mappedBy = "podcast")
    private Collection<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Collection<Episode> episodes = new ArrayList<>();

    public Podcast(int id, String apiId, Collection<User> users, Collection<Episode> episodes) {
        this.id = id;
        this.apiId = apiId;
        this.users = users;
        this.episodes = episodes;
    }

    public int getId() {
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

    public void setId(int id) {
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

