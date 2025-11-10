package com.cpaas_karaden.net;

import java.util.Map;

import com.cpaas_karaden.RequestOptions;
import com.cpaas_karaden.exception.KaradenException;

public interface RequestorInterface {
    ResponseInterface send(String method, String path, Map<String, ?> params, Map<String, ?> data, RequestOptions requestOptions, boolean isNoContents, boolean allowRedirects) throws KaradenException;
}
