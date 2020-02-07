package com.dominys.proxydemo.common.router;

import com.dominys.proxydemo.common.router.api.VersionResolver;
import com.dominys.proxydemo.common.router.api.annotation.ForceProxyVersion;
import com.dominys.proxydemo.common.router.api.annotation.RouteByCondition;
import com.dominys.proxydemo.common.router.api.exception.NoTargetForProxyException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProxyRouter<V extends Enum, D> implements InvocationHandler {

    private static final List<String> POJO_METHODS = List.of("equals", "hashCode", "toString");

    private ProxyRedirectProcessor<V> redirectProcessor;
    private VersionResolver<V, D> versionResolver;
    private Map<V, ?> versionsMap;
    private Class<D> dClass;
    private Class<V> vClass;

    public ProxyRouter(VersionResolver<V, D> versionResolver, Map<V, ?> beanVersions, Class<D> dClass, Class<V> vClass) {
        this.versionResolver = versionResolver;
        this.versionsMap = beanVersions;
        this.dClass = dClass;
        this.vClass = vClass;
        this.redirectProcessor = new ProxyRedirectProcessor<>(vClass);
    }

    @Override
    public Object invoke(Object target, Method method, Object[] args) throws Throwable { //NOSONAR: reflection
        return POJO_METHODS.contains(method.getName())
                ? method.invoke(this, args)
                : routeToTargetBean(method, args);
    }

    @SuppressWarnings("unchecked")
    private Object routeToTargetBean(Method method, Object[] args) throws Throwable { //NOSONAR: reflection
        ForceProxyVersion forceVersion = method.getAnnotation(ForceProxyVersion.class);
        V version;
        if (forceVersion != null){
            version = (V)Enum.valueOf(vClass, forceVersion.value()); //NOSONAR: sonar bug
        }
        else {
            version = getVersionFromResolver(method, args);
            version = redirectProcessor.getTargetVersion(version, method);
        }

        Object destination = versionsMap.get(version);
        if (destination == null){
            throw new NoTargetForProxyException("No target for proxy " + method.getDeclaringClass()
                    + " method " + method.getName() + " with version " + version);
        }
        try {
            return method.invoke(destination, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @SuppressWarnings("unchecked")
    private V getVersionFromResolver(Method method, Object[] args) {
        int index = ArrayUtils.indexOf(method.getParameterTypes(), dClass);
        RouteByCondition condition = method.getAnnotation(RouteByCondition.class);
        return Optional.ofNullable(condition)
                .map(item -> versionResolver.getRouteVersion((D) args[index], item.value()))
                .orElse(versionResolver.getRouteVersion((D) args[index]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Object target = o;
        if (target.getClass() == Proxy.class) {
            target = Proxy.getInvocationHandler(target);
        }
        if (getClass() != target.getClass()) {
            return false;
        }

        ProxyRouter<?, ?> that = (ProxyRouter<?, ?>) target;

        return new EqualsBuilder()
                .append(versionResolver, that.versionResolver)
                .append(versionsMap, that.versionsMap)
                .append(dClass, that.dClass)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(versionResolver)
                .append(versionsMap)
                .append(dClass)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("versionResolver", versionResolver)
                .append("versionsMap", versionsMap)
                .append("dClass", dClass)
                .toString();
    }
}