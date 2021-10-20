package com.nology.charactersAPI.models;

public class MarvelCharacter {
    private int id;
    private String name;
    private String description;
    private MarvelThumbnail marvelThumbnail;

    public MarvelCharacter(int id, String name, String description, MarvelThumbnail marvelThumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.marvelThumbnail = marvelThumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MarvelThumbnail getThumbnail() {
        return marvelThumbnail;
    }

    public void setThumbnail(MarvelThumbnail marvelThumbnail) {
        this.marvelThumbnail = marvelThumbnail;
    }
}

