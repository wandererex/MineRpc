package me.stevenkin.minerpc.rpc.protocol;

import me.stevenkin.minerpc.common.URL;
import me.stevenkin.minerpc.rpc.api.Exporter;
import me.stevenkin.minerpc.rpc.api.Invoker;

public class RestProtocol extends AbstractProtocol {
    @Override
    protected Exporter doExport(Invoker invoker) {
        return null;
    }

    @Override
    protected Invoker doRefer(URL url) {
        return null;
    }
}
