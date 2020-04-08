package me.stevenkin.minerpc.endpoint.client;

import me.stevenkin.minerpc.endpoint.Request;
import me.stevenkin.minerpc.endpoint.Response;
import me.stevenkin.minerpc.common.URL;

import java.util.concurrent.CompletableFuture;

public interface Client {
    Response send(Request request);

    CompletableFuture<Response> sendSync(Request request);

    void close();

    URL getUrl();
}
