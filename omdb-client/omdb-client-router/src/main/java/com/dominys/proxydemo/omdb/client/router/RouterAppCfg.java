package com.dominys.proxydemo.omdb.client.router;

import com.dominys.proxydemo.common.router.RoutedServiceProxyBeanFactory;
import com.dominys.proxydemo.common.router.api.VersionResolver;
import com.dominys.proxydemo.omdb.api.SupportedClientVersion;
import com.dominys.proxydemo.omdb.api.dto.User;
import org.springframework.context.annotation.*;

/**
 * @author rbezpalko
 * @since 25.10.17.
 */
@Configuration
@ComponentScan(basePackages = {"com.dominys.proxydemo.omdb"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class))
@Import({RoutedProxyBeansRegistrarCfg.class})
public class RouterAppCfg {

    @Bean
    public RoutedServiceProxyBeanFactory<SupportedClientVersion, User> routedServiceProxyBeanFactory(
            VersionResolver<SupportedClientVersion, User> versionResolver) {
        return new RoutedServiceProxyBeanFactory<>(versionResolver, User.class, SupportedClientVersion.class);
    }
}
