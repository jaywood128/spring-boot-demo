package com.johnathon.podcast_blast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name = "episode")


public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "podcast_id")
    private Podcast podcast;

    @ManyToMany(mappedBy = "user")
    private Collection<User> user = new ArrayList();

    public Episode(int id, Podcast podcast, Collection<User> user) {
        this.id = id;
        this.podcast = podcast;
        this.user = new ArrayList<User>();
    }
}

