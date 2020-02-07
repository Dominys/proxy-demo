package com.dominys.proxydemo.businesslogic;

import com.dominys.proxydemo.omdb.api.dto.OmdbApiSearchResponse;
import com.dominys.proxydemo.omdb.api.dto.SimpleMovie;
import com.dominys.proxydemo.omdb.api.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyService {

    private final OmdbClientFactory clientFactory;

    public OmdbApiSearchResponse<List<SimpleMovie>> searchMovie(User user, String title, int page) {
        return clientFactory.getClient(user).searchMovies(title, page);
    }

}
