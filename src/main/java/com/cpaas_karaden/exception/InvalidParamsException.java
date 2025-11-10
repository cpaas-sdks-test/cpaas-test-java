package com.cpaas_karaden.exception;

import com.cpaas_karaden.model.Error;

public class InvalidParamsException extends KaradenException {
    public InvalidParamsException(Error error) {
        super("", null, null, error);
    }
}
