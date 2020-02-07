package com.dominys.proxydemo.common.router.support;

import org.springframework.beans.factory.support.BeanDefinitionValidationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProxyCreationConfig<V> {

	private final Map<V, Class<?>> targetProxyClasses;

	private final Class<?> interfaceClass;

	private ProxyCreationConfig(Builder<V> builder) {
		this.targetProxyClasses = builder.targetProxyClasses;
		this.interfaceClass = builder.interfaceClass;
	}

	public Map<V, Class<?>> getTargetProxyClasses() {
		return targetProxyClasses;
	}

	public Class<?> getInterfaceClass() {
		return interfaceClass;
	}

	public static <V> Builder<V> builder(){
		return new Builder<>();
	}

	public static class Builder<V> {
		private Map<V, Class<?>> targetProxyClasses = new HashMap<>();

		private Class<?> interfaceClass;

		private void validate() {
			Optional<Class<?>> wrongTargetClass = targetProxyClasses.values()
					.stream()
					.filter(f -> !interfaceClass.isAssignableFrom(f))
					.findFirst();
			wrongTargetClass.ifPresent(aClass -> {
				throw new BeanDefinitionValidationException("Target bean class "
						+ aClass.getName() + " is not assignable from " + interfaceClass.getName());
			});
		}

		public Builder<V> addTarget(V version, Class<?> aClass){
			this.targetProxyClasses.put(version, aClass);
			return this;
		}

		public Builder<V> withInterfaceClass(Class<?> interfaceClass){
			this.interfaceClass = interfaceClass;
			return this;
		}

		public ProxyCreationConfig<V> build(){
			validate();
			return new ProxyCreationConfig<>(this);
		}
	}
}
