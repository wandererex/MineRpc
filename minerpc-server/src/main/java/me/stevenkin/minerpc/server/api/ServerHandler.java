package me.stevenkin.minerpc.server.api;

import me.stevenkin.minerpc.common.Request;
import me.stevenkin.minerpc.common.Response;

public interface ServerHandler {
    void handle(Request request, Response response);
}
