package com.dominys.proxydemo.businesslogic;

import com.dominys.proxydemo.omdb.api.OmdbClient;
import com.dominys.proxydemo.omdb.api.dto.OmdbApiSearchResponse;
import com.dominys.proxydemo.omdb.api.dto.Role;
import com.dominys.proxydemo.omdb.api.dto.SimpleMovie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyService {

    private final OmdbClient omdbClientV1;
    private final OmdbClient omdbClientV2;

    public OmdbApiSearchResponse<List<SimpleMovie>> searchMovie(Role role, String title, int page) {
        if (Role.FATHER.equals(role)) {
            return omdbClientV1.searchMovies(title, page);
        }
        return omdbClientV2.searchMovies(title, page);
    }

}
