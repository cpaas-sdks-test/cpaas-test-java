package com.cpaas_karaden.param.message;

import com.cpaas_karaden.exception.InvalidParamsException;

public abstract class MessageParams {
    public final static String CONTEXT_PATH = "/messages";
    public MessageParams validate() throws InvalidParamsException { return this; }
}
