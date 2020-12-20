package com.johnathon.podcast_blast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "episode")


public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiId;

    public String getApiId() {
        return apiId;
    }

    @ManyToOne
    // @JoinColumn annotation defines the actual physical mapping on the owning side //
    @JoinColumn(name="podcast_id")
    private Podcast podcast;

    @ManyToMany(mappedBy = "episodes")
    private List<User> user = new ArrayList<>();

    public Episode(Long id, Podcast podcast, String apiId) {
        this.id = id;
        this.podcast = podcast;
        this.apiId = apiId;
    }

    public Episode(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if(id != null){
            this.id = id;
        }
    }

    public Podcast getPodcast() {
        return podcast;
    }

    public void setPodcast(Podcast podcast) {
        if(podcast != null && this.podcast == null){
            this.podcast = podcast;
        }
    }

    public Collection<User> getUser() {
        return user;
    }

    public void setUser(User user) {
        if(!this.user.contains((user)) && user != null){
            this.user.add(user);
        }
    }
}

