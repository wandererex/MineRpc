package me.stevenkin.minerpc.endpoint.server;

import me.stevenkin.minerpc.common.URL;

public interface Server {
    void start();

    void stop();

    void setServerHandler(ServerHandler serverHandler);

    URL getUrl();
}
