package com.ae.apis.config.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Class<?> clazz, long id) {
        super(String.format(ApiResponseMessage.RESOURCE_NOT_FOUND, clazz.getSimpleName(), id));
    }

    public NotFoundException(Class<?> clazz, String id) {
        super(String.format(ApiResponseMessage.RESOURCE_NOT_FOUND, clazz.getSimpleName(), id));
    }

    public NotFoundException(Class<?> clazz, Object id) {
        super(String.format(ApiResponseMessage.RESOURCE_NOT_FOUND, clazz.getSimpleName(), id.toString()));
    }
}
