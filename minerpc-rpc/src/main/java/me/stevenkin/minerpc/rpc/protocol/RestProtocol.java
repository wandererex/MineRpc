package me.stevenkin.minerpc.rpc.protocol;

import me.stevenkin.minerpc.common.URL;
import me.stevenkin.minerpc.endpoint.client.Client;
import me.stevenkin.minerpc.endpoint.client.ClientFactory;
import me.stevenkin.minerpc.endpoint.server.Server;
import me.stevenkin.minerpc.endpoint.server.ServerFactory;
import me.stevenkin.minerpc.rpc.Exporter;
import me.stevenkin.minerpc.rpc.Invoker;
import me.stevenkin.minerpc.rpc.exporter.RestExporter;
import me.stevenkin.minerpc.rpc.invoker.RemoteInvoker;
import me.stevenkin.minerpc.spi.ExtensionLoader;

public class RestProtocol extends AbstractProtocol {
    @Override
    protected Exporter doExport(Invoker invoker) {
        if (!invoker.getUrl().getProtocol().equals("rest"))
            throw new IllegalArgumentException();
        Server server = ExtensionLoader.getExtensionLoader(ServerFactory.class).getAdaptiveExtension().getServer(invoker.getUrl());
        server.start();
        return new RestExporter(invoker, server, exporterCache);
    }

    @Override
    protected Invoker doRefer(URL url) {
        if (!url.getProtocol().equals("rest"))
            throw new IllegalArgumentException();
        Client client = ExtensionLoader.getExtensionLoader(ClientFactory.class).getAdaptiveExtension().getClient(url);
        return new RemoteInvoker(client, url);
    }
}
