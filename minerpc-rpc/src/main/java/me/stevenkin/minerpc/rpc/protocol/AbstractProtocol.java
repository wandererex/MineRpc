package me.stevenkin.minerpc.rpc.protocol;

import me.stevenkin.minerpc.common.URL;
import me.stevenkin.minerpc.rpc.Exporter;
import me.stevenkin.minerpc.rpc.Invoker;
import me.stevenkin.minerpc.rpc.Protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractProtocol implements Protocol {
    protected Map<String, Exporter> exporterCache = new ConcurrentHashMap<>();

    protected Map<String, Invoker> invokerCache = new ConcurrentHashMap<>();

    @Override
    public Exporter export(Invoker invoker) {
        String fullString = invoker.getUrl().toFullString();
        if (exporterCache.containsKey(fullString))
            return exporterCache.get(fullString);
        synchronized (exporterCache) {
            if (!exporterCache.containsKey(fullString)){
                exporterCache.put(fullString, doExport(invoker));
            }
        }
        return exporterCache.get(fullString);
    }

    protected abstract Exporter doExport(Invoker invoker);

    @Override
    public Invoker refer(URL url) {
        String fullString = url.toFullString();
        if (invokerCache.containsKey(fullString))
            return invokerCache.get(fullString);
        synchronized (invokerCache) {
            if (!invokerCache.containsKey(fullString)){
                invokerCache.put(fullString, doRefer(url));
            }
        }
        return invokerCache.get(fullString);
    }

    protected abstract Invoker doRefer(URL url);

    @Override
    public void destroy() {
        invokerCache.values().forEach(Invoker::destroy);
    }
}
