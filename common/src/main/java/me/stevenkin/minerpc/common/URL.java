package me.stevenkin.minerpc.common;

import java.util.HashMap;
import java.util.Map;

public class URL {
    private final String protocol;

    private final String username;

    private final String password;

    private final String host;

    private final int port;

    private final String path;

    private final Map<String, String> parameters;

    public URL() {
        this.protocol = null;
        this.username = null;
        this.password = null;
        this.host = null;
        this.port = 0;
        this.path = null;
        this.parameters = null;
    }

    public Map<String, String> getParameters() {
        return new HashMap<>(parameters);
    }
}
