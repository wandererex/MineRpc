package me.stevenkin.minerpc.registry.api;

import me.stevenkin.minerpc.common.URL;

import java.util.List;

public interface NotifyListener {
    void notify(List<URL> urls);
}
