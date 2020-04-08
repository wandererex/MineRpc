package me.stevenkin.minerpc.endpoint.server;

import me.stevenkin.minerpc.endpoint.Request;
import me.stevenkin.minerpc.endpoint.Response;

public interface ServerHandler {
    void handle(Request request, Response response);
}
