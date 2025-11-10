package com.cpaas_karaden.model;

public interface ErrorInterface {
    String getCode();
    String getMessage();
    KaradenObjectInterface getErrors();
}
