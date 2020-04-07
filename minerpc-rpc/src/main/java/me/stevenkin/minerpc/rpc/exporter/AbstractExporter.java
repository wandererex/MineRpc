package me.stevenkin.minerpc.rpc.exporter;

import me.stevenkin.minerpc.rpc.Exporter;
import me.stevenkin.minerpc.rpc.Invoker;

import java.util.Map;

public abstract class AbstractExporter implements Exporter {
    private final Invoker invoker;

    private final Map<String, Exporter> exporterCache;

    private boolean unexported = false;

    public AbstractExporter(Invoker invoker, Map<String, Exporter> exporterCache) {
        this.invoker = invoker;
        this.exporterCache = exporterCache;
    }

    @Override
    public Invoker getInvoker() {
        return invoker;
    }

    @Override
    public synchronized void unexport() {
        if (unexported)
            return;
        unexported = true;
        doUnexport();
        invoker.destroy();
        exporterCache.remove(invoker.getUrl().toFullString());
    }

    protected abstract void doUnexport();
}
