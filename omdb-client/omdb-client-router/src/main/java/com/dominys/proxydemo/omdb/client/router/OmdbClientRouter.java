package com.dominys.proxydemo.omdb.client.router;

import com.dominys.proxydemo.omdb.api.OmdbClient;
import com.dominys.proxydemo.omdb.api.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("omdbClientRouter")
@RequiredArgsConstructor
public class OmdbClientRouter implements OmdbClient {

    private final OmdbClient omdbClientV1;
    private final OmdbClient omdbClientV2;

    @Override
    public OmdbApiSearchResponse<List<SimpleMovie>> searchMovies(User user, String title, int page) {
        return getClient(user).searchMovies(user, title, page);
    }

    @Override
    public OmdbApiResponse<OmdbMovie> searchByImdbId(String imdbId) {
        throw new UnsupportedOperationException();
    }

    public OmdbClient getClient(User user) {
        if (Role.FATHER.equals(user.getRole())) {
            return omdbClientV1;
        }
        return omdbClientV2;
    }
}
