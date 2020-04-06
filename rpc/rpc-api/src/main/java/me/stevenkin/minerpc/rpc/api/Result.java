package me.stevenkin.minerpc.rpc.api;

import java.util.Map;

public interface Result {
    Object getValue();

    Throwable getException();

    boolean hasException();

    Map<String, String> getAttachments();

}