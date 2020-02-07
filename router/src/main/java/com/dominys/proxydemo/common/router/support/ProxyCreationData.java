package com.dominys.proxydemo.common.router.support;

import java.util.List;

public class ProxyCreationData<V> {

	private final ClassLoader classLoader;
	private final Class<?>[] interfaces;
	private final List<TargetBeanInfo<V>> targetBeans;

	public ProxyCreationData(ClassLoader classLoader, Class<?>[] interfaces,
							 List<TargetBeanInfo<V>> targetBeans) {
		this.classLoader = classLoader;
		this.interfaces = interfaces;
		this.targetBeans = targetBeans;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public Class<?>[] getInterfaces() {
		return interfaces;
	}

	public List<TargetBeanInfo<V>> getTargetBeans() {
		return targetBeans;
	}

}
