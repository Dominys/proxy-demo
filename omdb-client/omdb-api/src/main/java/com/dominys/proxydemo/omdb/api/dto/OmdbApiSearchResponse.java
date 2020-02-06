package com.dominys.proxydemo.omdb.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OmdbApiSearchResponse<T> extends OmdbApiResponse<T> {

    @JsonProperty("totalResults")
    private int totalResults;
}
