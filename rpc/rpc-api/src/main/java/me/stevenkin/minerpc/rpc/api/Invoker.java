package me.stevenkin.minerpc.rpc.api;

import me.stevenkin.minerpc.common.URL;

public interface Invoker {
    Result invoke(Invocation invocation);

    URL getUrl();

    void destroy();
}
