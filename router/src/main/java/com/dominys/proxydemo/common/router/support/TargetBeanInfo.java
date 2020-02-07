package com.dominys.proxydemo.common.router.support;

public class TargetBeanInfo<T> {

	private T version;
	private String beanName;
	private Class<?> beanType;

	public TargetBeanInfo(T version, String beanName, Class<?> beanType) {
		this.version = version;
		this.beanName = beanName;
		this.beanType = beanType;
	}

	public T getVersion() {
		return version;
	}

	public String getBeanName() {
		return beanName;
	}

	public Class<?> getBeanType() {
		return beanType;
	}
}
