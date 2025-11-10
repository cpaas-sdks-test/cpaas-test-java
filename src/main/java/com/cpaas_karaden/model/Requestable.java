package com.cpaas_karaden.model;


import java.util.Map;

import com.cpaas_karaden.RequestOptions;
import com.cpaas_karaden.exception.KaradenException;
import com.cpaas_karaden.net.OkHttpRequestor;
import com.cpaas_karaden.net.ResponseInterface;


abstract class Requestable extends KaradenObject {
    protected static OkHttpRequestor request;

    static {
        Message.request = new OkHttpRequestor();
    }

    public Requestable() {
        super();
    }

    public Requestable(Object id, RequestOptions requestOptions) {
        super(id, requestOptions);
    }

    protected static KaradenObject request(String method, String path, Map<String, ?> params, Map<String, ?> data, RequestOptions requestOptions) throws KaradenException {
        ResponseInterface response = Requestable.request.send(method, path, params, data, requestOptions, false, true);
        if (response.isError()) {
            throw response.getError();
        }
        return response.getObject();
    }

    protected static ResponseInterface requestAndReturnResponseInterface(String method, String path, Map<String, ?> params, Map<String, ?> data, RequestOptions requestOptions) throws KaradenException {
        ResponseInterface response = Requestable.request.send(method, path, params, data, requestOptions, true, false);
        if (response.isError()) {
            throw response.getError();
        }
        return response;
    }
}
