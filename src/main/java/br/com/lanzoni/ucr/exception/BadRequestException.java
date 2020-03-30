package br.com.lanzoni.ucr.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(message);
    }

    public static HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}
