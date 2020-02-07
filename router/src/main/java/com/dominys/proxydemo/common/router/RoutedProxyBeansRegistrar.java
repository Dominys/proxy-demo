package com.dominys.proxydemo.common.router;

import com.dominys.proxydemo.common.router.support.ProxyCreationConfig;
import com.dominys.proxydemo.common.router.support.ProxyCreationData;
import com.dominys.proxydemo.common.router.support.TargetBeanInfo;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.ResolvableType;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class RoutedProxyBeansRegistrar<V> implements ImportBeanDefinitionRegistrar, BeanClassLoaderAware {

    private ClassLoader classLoader;

    protected abstract List<ProxyCreationConfig<V>> getProxyConfig();

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        getProxyConfig().forEach(config -> registerProxy(registry, config));
    }

    private void registerProxy(BeanDefinitionRegistry registry, ProxyCreationConfig<V> config) {
        List<TargetBeanInfo<V>> targetBeans = new ArrayList<>();
        for (Map.Entry<V, Class<?>> entry : config.getTargetProxyClasses().entrySet()){
            BeanDefinition beanDefinition = BeanDefinitionBuilder
                    .genericBeanDefinition(entry.getValue())
                    .setScope(BeanDefinition.SCOPE_SINGLETON)
                    .getBeanDefinition();
            beanDefinition.setAutowireCandidate(false);
            String name = BeanDefinitionReaderUtils.generateBeanName(beanDefinition, registry);
            registry.registerBeanDefinition(name, beanDefinition);

            targetBeans.add(new TargetBeanInfo<>(entry.getKey(), name, entry.getValue()));
        }

        createServiceProxies(config.getInterfaceClass(), targetBeans, registry);
    }

    private void createServiceProxies(Class<?> interfaceClass, List<TargetBeanInfo<V>> targetBeanInfos,
                                      BeanDefinitionRegistry registry) {
        RootBeanDefinition proxyBeanDefinition = new RootBeanDefinition();
        proxyBeanDefinition.setBeanClass(interfaceClass);
        proxyBeanDefinition.setTargetType(ResolvableType.forClass(interfaceClass).resolve());
        ConstructorArgumentValues args = new ConstructorArgumentValues();
        ProxyCreationData<V> creationData = new ProxyCreationData<>(classLoader, new Class<?>[]{interfaceClass},
                targetBeanInfos);
        args.addGenericArgumentValue(creationData);
        proxyBeanDefinition.setConstructorArgumentValues(args);
        proxyBeanDefinition.setFactoryBeanName("routedServiceProxyBeanFactory");
        proxyBeanDefinition.setFactoryMethodName("createRoutedProxyBean");

        String name = BeanDefinitionReaderUtils.generateBeanName(proxyBeanDefinition, registry);
        registry.registerBeanDefinition(name, proxyBeanDefinition);
    }

}