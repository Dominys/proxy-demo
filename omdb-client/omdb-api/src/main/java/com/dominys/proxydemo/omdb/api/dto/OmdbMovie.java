package com.dominys.proxydemo.omdb.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OmdbMovie {

    @JsonProperty("Title")
    private String title;
    @JsonProperty("Released")
    private String released;
    @JsonProperty("imdbRating")
    private String imdbRating;
    @JsonProperty("imdbVotes")
    private String imdbVotes;
    @JsonProperty("Runtime")
    private String runtime;
    @JsonProperty("Director")
    private String director;
    @JsonProperty("Plot")
    private String plot;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("imdbID")
    private String imdbId;
    @JsonProperty("Genre")
    private String genres;
    @JsonProperty("Actors")
    private String actors;
    @JsonProperty("Language")
    private String languages;
    @JsonProperty("Country")
    private String countries;
}
