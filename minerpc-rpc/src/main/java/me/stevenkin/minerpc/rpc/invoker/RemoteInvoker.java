package me.stevenkin.minerpc.rpc.invoker;

import me.stevenkin.minerpc.common.URL;
import me.stevenkin.minerpc.endpoint.client.Client;
import me.stevenkin.minerpc.rpc.Invocation;
import me.stevenkin.minerpc.rpc.Invoker;
import me.stevenkin.minerpc.rpc.Result;

public class RemoteInvoker implements Invoker {
    private final Client client;

    private final URL url;

    private boolean destroyed = false;

    public RemoteInvoker(Client client, URL url) {
        this.client = client;
        this.url = url;
    }

    @Override
    public Result invoke(Invocation invocation) {
        return null;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public synchronized void destroy() {
        if (destroyed)
            return;
        destroyed = true;
        client.close();
    }
}
