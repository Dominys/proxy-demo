package com.dominys.proxydemo.omdb.v2.impl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("omdbClientConfigV2")
@PropertySource("classpath:/omdb.properties")
@ConfigurationProperties(prefix = "omdb")
@Data
public class OmdbClientConfig implements ApiClientConfig {
    private String url;
    private String apiKey;
}
