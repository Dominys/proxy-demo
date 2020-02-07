package com.dominys.proxydemo.common.router.api.exception;

public class NoTargetForProxyException extends RuntimeException {

    public NoTargetForProxyException(String message) {
        super(message);
    }
}
