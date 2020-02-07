package com.dominys.proxydemo.common.router.api;

public interface VersionResolver<V, D> {
	V getRouteVersion(D userData);

	V getRouteVersion(D userData, String condition);
}
