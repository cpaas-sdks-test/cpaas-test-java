package com.cpaas_karaden.net;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.cpaas_karaden.RequestOptions;
import com.cpaas_karaden.Utility;
import com.cpaas_karaden.exception.BadRequestException;
import com.cpaas_karaden.exception.FileNotFoundException;
import com.cpaas_karaden.exception.KaradenException;
import com.cpaas_karaden.exception.ForbiddenException;
import com.cpaas_karaden.exception.NotFoundException;
import com.cpaas_karaden.exception.TooManyRequestsException;
import com.cpaas_karaden.exception.UnauthorizedException;
import com.cpaas_karaden.exception.UnexpectedValueException;
import com.cpaas_karaden.exception.UnknownErrorException;
import com.cpaas_karaden.exception.UnprocessableEntityException;
import com.cpaas_karaden.model.KaradenObject;
import okhttp3.Headers;
import com.cpaas_karaden.model.Error;
import com.cpaas_karaden.model.ErrorInterface;


public class OkHttpResponse implements ResponseInterface {
    protected final static Map<Integer, Class<?>> errors = new HashMap<>();

    static {
        OkHttpResponse.errors.put(BadRequestException.STATUS_CODE, BadRequestException.class);
        OkHttpResponse.errors.put(UnauthorizedException.STATUS_CODE, UnauthorizedException.class);
        OkHttpResponse.errors.put(NotFoundException.STATUS_CODE, NotFoundException.class);
        OkHttpResponse.errors.put(ForbiddenException.STATUS_CODE, ForbiddenException.class);
        OkHttpResponse.errors.put(TooManyRequestsException.STATUS_CODE, TooManyRequestsException.class);
        OkHttpResponse.errors.put(UnprocessableEntityException.STATUS_CODE, UnprocessableEntityException.class);
    }

    protected KaradenObject object = null;
    protected KaradenException error = null;

    public KaradenObject getObject() {
        return this.object;
    }

    public KaradenException getError() {
        return this.error;
    }

    public int getStatusCode() {
        throw new UnsupportedOperationException();
    }

    public Headers getHeaders() {
        throw new UnsupportedOperationException();
    }

    public boolean isError() {
        return this.error != null;
    }

    public OkHttpResponse(okhttp3.Response response, RequestOptions requestOptions) {
        this.interpret(response, requestOptions);
    }

    protected void interpret(okhttp3.Response response, RequestOptions requestOptions) {
        KaradenObject object = null;
        int statusCode = response.code();
        String body = null;
        try {
            body = response.body().string();
            JSONObject contents = new JSONObject(body);
            object = Utility.convertToKaradenObject(contents, requestOptions);
        } catch (Exception e) {
            Map<String, List<String>> headers = response.headers().toMultimap();
            this.error = new UnexpectedValueException(statusCode, headers, body);
            return;
        }

        if (200 > statusCode || 400 <= statusCode) {
            Map<String, List<String>> headers = response.headers().toMultimap();
            this.error = object.getObject() != null && object.getObject().equals("error") ?
                this.handleError(statusCode, headers, body, (Error)object) :
                new UnexpectedValueException(statusCode, headers, body);
        }

        this.object = object;
    }

    protected KaradenException handleError(int statusCode, Map<String, List<String>> headers, String body, Error error) {
        Class<?> cls = OkHttpResponse.errors.get(statusCode);
        if (cls == null) {
            return new UnknownErrorException(statusCode, headers, body);
        } else {
            KaradenException exception = null;
            try {
                Constructor<?> constructor = cls.getConstructor(Map.class, String.class, ErrorInterface.class);
                exception = (KaradenException)constructor.newInstance(headers, body, error);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            return exception;
        }
    }
}
