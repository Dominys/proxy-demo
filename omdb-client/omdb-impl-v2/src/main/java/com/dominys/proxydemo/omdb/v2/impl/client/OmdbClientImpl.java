package com.dominys.proxydemo.omdb.v2.impl.client;

import com.dominys.proxydemo.omdb.api.OmdbClient;
import com.dominys.proxydemo.omdb.api.dto.OmdbApiResponse;
import com.dominys.proxydemo.omdb.api.dto.OmdbApiSearchResponse;
import com.dominys.proxydemo.omdb.api.dto.OmdbMovie;
import com.dominys.proxydemo.omdb.api.dto.SimpleMovie;
import com.dominys.proxydemo.omdb.v2.impl.config.ApiClientConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service("omdbClientV2")
@RequiredArgsConstructor
@Slf4j
public class OmdbClientImpl implements OmdbClient {

    private final ApiClientConfig omdbClientConfigV2;
    private final RestTemplate omdbClientRestTemplateV2;

    @Override
    public OmdbApiSearchResponse<List<SimpleMovie>> searchMovies(String title, int page) {
        log.warn("Search movies v2");

        String url = UriComponentsBuilder.fromHttpUrl(omdbClientConfigV2.getUrl())
                .queryParam("apikey", omdbClientConfigV2.getApiKey())
                .queryParam("r", "json")
                .queryParam("s", title.replace("Porn", "Pony"))
                .queryParam("page", page)
                .toUriString();
        return sendGetRequestToApi(url, new ParameterizedTypeReference<OmdbApiSearchResponse<List<SimpleMovie>>>() {
        });
    }

    @Override
    public OmdbApiResponse<OmdbMovie> searchByImdbId(String imdbId) {
        String url = UriComponentsBuilder.fromHttpUrl(omdbClientConfigV2.getUrl())
                .queryParam("apikey", omdbClientConfigV2.getApiKey())
                .queryParam("i", imdbId)
                .toUriString();
        return sendGetRequestToApi(url, new ParameterizedTypeReference<OmdbApiResponse<OmdbMovie>>() {
        });
    }

    private <T> T sendGetRequestToApi(String url, ParameterizedTypeReference<T> typeReference) {
        return omdbClientRestTemplateV2.exchange(
                url,
                HttpMethod.GET,
                null,
                typeReference)
                .getBody();
    }
}
