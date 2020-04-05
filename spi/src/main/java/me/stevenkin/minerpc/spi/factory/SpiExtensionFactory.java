package me.stevenkin.minerpc.spi.factory;

import me.stevenkin.minerpc.spi.ExtensionFactory;
import me.stevenkin.minerpc.spi.ExtensionLoader;

public class SpiExtensionFactory implements ExtensionFactory {
    @Override
    public <T> T getExtension(Class<T> type, String name) {
        return ExtensionLoader.getExtensionLoader(type).getExtension(name);
    }
}
