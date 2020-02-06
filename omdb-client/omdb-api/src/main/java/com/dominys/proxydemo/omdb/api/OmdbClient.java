package com.dominys.proxydemo.omdb.api;

import com.dominys.proxydemo.omdb.api.dto.OmdbApiResponse;
import com.dominys.proxydemo.omdb.api.dto.OmdbApiSearchResponse;
import com.dominys.proxydemo.omdb.api.dto.OmdbMovie;
import com.dominys.proxydemo.omdb.api.dto.SimpleMovie;

import java.util.List;

public interface OmdbClient {

    OmdbApiSearchResponse<List<SimpleMovie>> searchMovies(String title, int page);

    OmdbApiResponse<OmdbMovie> searchByImdbId(String imdbId);
}
