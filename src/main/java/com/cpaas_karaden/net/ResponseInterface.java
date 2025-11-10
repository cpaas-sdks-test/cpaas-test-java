package com.cpaas_karaden.net;

import com.cpaas_karaden.exception.KaradenException;
import com.cpaas_karaden.model.KaradenObject;
import okhttp3.Headers;


public interface ResponseInterface {
    KaradenObject getObject();
    KaradenException getError();
    int getStatusCode();
    Headers getHeaders();
    boolean isError();
}
