package com.cpaas_karaden.exception;

import com.cpaas_karaden.model.Error;

public class InvalidRequestOptionsException extends KaradenException {
    public InvalidRequestOptionsException(Error error) {
        super("", null, null, error);
    }
}
