package com.dominys.proxydemo.businesslogic;

import com.dominys.proxydemo.omdb.api.OmdbClient;
import com.dominys.proxydemo.omdb.api.dto.Role;
import com.dominys.proxydemo.omdb.api.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OmdbClientFactory {

    private final OmdbClient omdbClientV1;
    private final OmdbClient omdbClientV2;

    public OmdbClient getClient(User user) {
        if (Role.FATHER.equals(user.getRole())) {
            return omdbClientV1;
        }
        return omdbClientV2;
    }
}
