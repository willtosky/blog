package cn.crybird.manage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ViewNotFoundException extends RuntimeException {

    public ViewNotFoundException(String message) {
        super(message);
    }
}
