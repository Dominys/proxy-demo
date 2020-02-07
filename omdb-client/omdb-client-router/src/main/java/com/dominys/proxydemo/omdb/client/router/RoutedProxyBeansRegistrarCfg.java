package com.dominys.proxydemo.omdb.client.router;

import com.dominys.proxydemo.common.router.RoutedProxyBeansRegistrar;
import com.dominys.proxydemo.common.router.support.ProxyCreationConfig;
import com.dominys.proxydemo.omdb.api.OmdbClient;
import com.dominys.proxydemo.omdb.api.SupportedClientVersion;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rbezpalko
 * @since 24.10.17.
 */
@Configuration
public class RoutedProxyBeansRegistrarCfg extends RoutedProxyBeansRegistrar<SupportedClientVersion> {

    @Override
    protected List<ProxyCreationConfig<SupportedClientVersion>> getProxyConfig() {
        List<ProxyCreationConfig<SupportedClientVersion>> configs = new ArrayList<>();

        configs.add(ProxyCreationConfig.<SupportedClientVersion>builder()
                .withInterfaceClass(OmdbClient.class)
                .addTarget(SupportedClientVersion.VERSION_1, com.dominys.proxydemo.omdb.v1.impl.client.OmdbClientImpl.class)
                .addTarget(SupportedClientVersion.VERSION_2, com.dominys.proxydemo.omdb.v2.impl.client.OmdbClientImpl.class)
                .build());

        return configs;
    }
}