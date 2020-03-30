package br.com.lanzoni.ucr.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;

    public UnauthorizedException(String message) {
        super(message);
    }

    public static HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }

}
