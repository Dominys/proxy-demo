package com.dominys.proxydemo.common.router;

import com.dominys.proxydemo.common.router.api.annotation.RedirectRoute;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class ProxyRedirectProcessor<V extends Enum> {

    private final Map<Method, Map<V, V>> methodRedirects = new ConcurrentHashMap<>();
    private Class<V> vClass;

    public ProxyRedirectProcessor(Class<V> vClass) {
        this.vClass = vClass;
    }

    @SuppressWarnings("unchecked")
    public V getTargetVersion(V version, Method method) {
        RedirectRoute redirectRoute = method.getAnnotation(RedirectRoute.class);
        if (redirectRoute == null){
            return version;
        }
        Map<V, V> routes = methodRedirects.computeIfAbsent(method, m ->
                Arrays.stream(redirectRoute.value())
                        .collect(Collectors.toMap(k -> (V)Enum.valueOf(vClass, k.from()),
                                v -> (V)Enum.valueOf(vClass, v.to()))));
        return routes.getOrDefault(version, version);
    }

}
