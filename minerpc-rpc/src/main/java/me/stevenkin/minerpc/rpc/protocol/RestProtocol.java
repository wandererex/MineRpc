package me.stevenkin.minerpc.rpc.protocol;

import me.stevenkin.minerpc.common.URL;
import me.stevenkin.minerpc.rpc.Exporter;
import me.stevenkin.minerpc.rpc.Invoker;
import me.stevenkin.minerpc.rpc.exporter.RestExporter;
import me.stevenkin.minerpc.server.api.Server;
import me.stevenkin.minerpc.server.api.ServerFactory;
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
        return null;
    }
}
