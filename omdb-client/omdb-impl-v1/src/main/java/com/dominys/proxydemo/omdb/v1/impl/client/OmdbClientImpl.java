package com.dominys.proxydemo.omdb.v1.impl.client;

import com.dominys.proxydemo.omdb.api.OmdbClient;
import com.dominys.proxydemo.omdb.api.dto.OmdbApiResponse;
import com.dominys.proxydemo.omdb.api.dto.OmdbApiSearchResponse;
import com.dominys.proxydemo.omdb.api.dto.OmdbMovie;
import com.dominys.proxydemo.omdb.api.dto.SimpleMovie;
import com.dominys.proxydemo.omdb.v1.impl.config.ApiClientConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OmdbClientImpl implements OmdbClient {

    private final ApiClientConfig clientConfig;
    private final RestTemplate restTemplate;

    @Override
    public OmdbApiSearchResponse<List<SimpleMovie>> searchMovies(String title, int page) {
        String url = UriComponentsBuilder.fromHttpUrl(clientConfig.getUrl())
                .queryParam("apikey", clientConfig.getApiKey())
                .queryParam("r", "json")
                .queryParam("s", title)
                .queryParam("page", page)
                .toUriString();
        return sendGetRequestToApi(url, new ParameterizedTypeReference<OmdbApiSearchResponse<List<SimpleMovie>>>() {
        });
    }

    @Override
    public OmdbApiResponse<OmdbMovie> searchByImdbId(String imdbId) {
        String url = UriComponentsBuilder.fromHttpUrl(clientConfig.getUrl())
                .queryParam("apikey", clientConfig.getApiKey())
                .queryParam("i", imdbId)
                .toUriString();
        return sendGetRequestToApi(url, new ParameterizedTypeReference<OmdbApiResponse<OmdbMovie>>() {
        });
    }

    private <T> T sendGetRequestToApi(String url, ParameterizedTypeReference<T> typeReference) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                typeReference)
                .getBody();
    }
}
