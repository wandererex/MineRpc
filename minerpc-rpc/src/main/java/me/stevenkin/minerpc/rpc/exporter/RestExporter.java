package me.stevenkin.minerpc.rpc.exporter;

import me.stevenkin.minerpc.rpc.Exporter;
import me.stevenkin.minerpc.rpc.Invoker;
import me.stevenkin.minerpc.server.api.Server;

import java.util.Map;

public class RestExporter extends AbstractExporter {
    private final Server server;

    public RestExporter(Invoker invoker, Server server, Map<String, Exporter> exporterCache) {
        super(invoker, exporterCache);
        this.server = server;
    }

    @Override
    protected void doUnexport() {
        server.stop();
    }
}
