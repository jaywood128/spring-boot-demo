package com.johnathon.podcast_blast.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "podcast")

public class Podcast {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String apiId;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "podcasts")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "podcast")
    private Set<Episode> episodes = new HashSet<>();

    public Podcast(String apiId) throws IllegalArgumentException {
        this.apiId = apiId;
    }
    public Podcast(){ }

    public Long getId() {
        return id;
    }

    public String getApiId() {
        return apiId;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public Set<Episode> getEpisodes() {
        return episodes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public boolean setUser(User user) {
        if(this.users.add(user)){
            System.out.println("setting PODCAST with ID: " + this.getApiId() + "to USER: " + user.getUserName());
            Iterator it = this.users.iterator();
            System.out.println("PODCAST with ID: " + this.getApiId() + " USER's --->: ");
            if(it.hasNext()){
                System.out.println(it.next());
            }
            return true;
        }
        return false;
    }
    public boolean removeUser(User user){
        System.out.println("Inside podcast remove user: " + this.getUsers().contains(user));
        if (user != null) {
            System.out.print(this.apiId + " has been removed for " + user.getUserName());
            return this.users.remove(user);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "id=" + id +
                ", apiId='" + apiId + '\'' +
                ", users=" + users +
                ", episodes=" + episodes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Podcast podcast = (Podcast) o;
        System.out.println("PODCAST in equals --> " + podcast.toString());
        System.out.println("USER in equals -->" + this.toString());
        return this.apiId.equals(podcast.apiId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiId);
    }

    public void setEpisodes(Episode episode) {
        if (episode != null) {
            this.episodes.add(episode);
        }
    }


}

