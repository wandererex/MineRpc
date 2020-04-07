package me.stevenkin.minerpc.client.api;

import me.stevenkin.minerpc.common.Request;
import me.stevenkin.minerpc.common.Response;
import me.stevenkin.minerpc.common.URL;

import java.util.concurrent.CompletableFuture;

public interface Client {
    boolean connect();

    Response send(Request request);

    CompletableFuture<Response> sendSync(Request request);

    void close();

    URL getUrl();
}
