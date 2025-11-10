package com.cpaas_karaden.model;

import com.cpaas_karaden.RequestOptions;


public class Error extends KaradenObject implements ErrorInterface {
    public final static String OBJECT_NAME = "error";

    public Error() {
        super();
    }

    public Error(Object id, RequestOptions requestOptions) {
        super(id, requestOptions);
    }

    public String getCode() {
        return (String)this.getProperty("code");
    }

    public String getMessage() {
        return (String)this.getProperty("message");
    }

    public KaradenObjectInterface getErrors() {
        return (KaradenObjectInterface)this.getProperty("errors");
    }
}
