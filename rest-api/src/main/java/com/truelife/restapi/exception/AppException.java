package com.truelife.restapi.exception;


import lombok.Getter;

public class AppException {

    public static class RunningException extends RuntimeException {
        public RunningException(String message) {
            super(message);
        }
        public RunningException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @Getter
    public static class ResourceNotFoundException extends RuntimeException {
        private String resourceName;
        private String fieldName;
        private Object fieldValue;

        public ResourceNotFoundException( String resourceName, String fieldName, Object fieldValue) {
            super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
            this.resourceName = resourceName;
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
        }
    }

    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }

        public BadRequestException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}