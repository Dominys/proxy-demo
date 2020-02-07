package com.dominys.proxydemo.omdb.client.router;

import com.dominys.proxydemo.common.router.api.VersionResolver;
import com.dominys.proxydemo.omdb.api.SupportedClientVersion;
import com.dominys.proxydemo.omdb.api.dto.Role;
import com.dominys.proxydemo.omdb.api.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoutingVersionResolver implements VersionResolver<SupportedClientVersion, User> {

    @Override
    public SupportedClientVersion getRouteVersion(User user) {
        if (Role.FATHER.equals(user.getRole())) {
            return SupportedClientVersion.VERSION_1;
        }
        return SupportedClientVersion.VERSION_2;
    }

    @Override
    public SupportedClientVersion getRouteVersion(User user, String condition) {
        if (Role.FATHER.equals(user.getRole())) {
            return SupportedClientVersion.VERSION_1;
        }
        return SupportedClientVersion.VERSION_2;
    }
}
