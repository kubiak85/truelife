package com.truelife.restapi.exception;

public class AuthException {
    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
        public EntityNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class DuplicateEntityException extends RuntimeException {
        public DuplicateEntityException(String message) {
            super(message);
        }
        public DuplicateEntityException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}