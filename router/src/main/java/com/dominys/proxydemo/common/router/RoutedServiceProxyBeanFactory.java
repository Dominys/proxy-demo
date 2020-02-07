package com.dominys.proxydemo.common.router;

import com.dominys.proxydemo.common.router.api.annotation.ForceProxyVersion;
import com.dominys.proxydemo.common.router.api.ProxyBeanFactory;
import com.dominys.proxydemo.common.router.api.VersionResolver;
import com.dominys.proxydemo.common.router.support.ProxyCreationData;
import com.dominys.proxydemo.common.router.support.TargetBeanInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.stream.Collectors;

public class RoutedServiceProxyBeanFactory<V extends Enum, D> implements BeanFactoryAware, ProxyBeanFactory<V> {

    private VersionResolver<V, D> versionResolver;
    private BeanFactory beanFactory;
    private Class<D> dClass;
    private Class<V> vClass;

    public RoutedServiceProxyBeanFactory(VersionResolver<V, D> versionResolver, Class<D> dClass, Class<V> vClass) {
        this.versionResolver = versionResolver;
        this.dClass = dClass;
        this.vClass = vClass;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T createRoutedProxyBean(ProxyCreationData<V> creationData) {
        validateInterfaces(creationData);
        Map<V, ?> versionsMap = creationData
                .getTargetBeans()
                .stream()
                .collect(Collectors.toMap(TargetBeanInfo::getVersion,
                        f -> beanFactory.getBean(f.getBeanName(), f.getBeanType())));

        return (T) Proxy.newProxyInstance(creationData.getClassLoader(), creationData.getInterfaces(),
                new ProxyRouter<>(versionResolver, versionsMap, dClass, vClass));
    }

    private void validateInterfaces(ProxyCreationData<V> creationData) {
        for(Class<?> aClass : creationData.getInterfaces()) {
            validateMethods(aClass);
        }
    }

    private void validateMethods(Class<?> aClass) {
        for (Method method: aClass.getDeclaredMethods()){
            if (!ArrayUtils.contains(method.getParameterTypes(), dClass)
                    && !method.isAnnotationPresent(ForceProxyVersion.class)){
                throw new BeanCreationException("Proxy interface " + aClass.getName()
                        + " method " + method.getName() + " does not contain parameter with type " + dClass.getName()
                        + " for version resolving.");
            }
        }
    }
}
