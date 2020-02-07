package com.dominys.proxydemo.common.router.api;

import com.dominys.proxydemo.common.router.support.ProxyCreationData;

@FunctionalInterface
public interface ProxyBeanFactory<V> {
	<T> T createRoutedProxyBean(ProxyCreationData<V> creationData);
}
