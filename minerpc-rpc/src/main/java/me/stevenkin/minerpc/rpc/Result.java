package me.stevenkin.minerpc.rpc;

import java.util.HashMap;
import java.util.Map;

public class Result {
    private final Object value;

    private final Throwable exception;

    private final Map<String, Object> attachs;

    public Result(Object value, Throwable exception, Map<String, Object> attachs) {
        this.value = value;
        this.exception = exception;
        this.attachs = attachs;
    }

    public boolean hasException() {
        return exception != null;
    }

    public Object getValue() {
        return value;
    }

    public Throwable getException() {
        return exception;
    }

    public Map<String, Object> getAttachs() {
        return new HashMap<>(attachs);
    }
}