package me.stevenkin.minerpc.spi.factory;

import me.stevenkin.minerpc.spi.Adaptive;
import me.stevenkin.minerpc.spi.ExtensionFactory;
import me.stevenkin.minerpc.spi.ExtensionLoader;

import java.util.Set;
import java.util.stream.Collectors;

@Adaptive
public class AdaptiveExtensionFactory implements ExtensionFactory {
    @Override
    public <T> T getExtension(Class<T> type, String name) {
        Set<ExtensionFactory> factories = ExtensionLoader.getExtensionLoader(ExtensionFactory.class)
                .getExtensionNames().stream().map(n -> ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getExtension(n)).collect(Collectors.toSet());
        for (ExtensionFactory factory : factories) {
            T extension = factory.getExtension(type, name);
            if (extension != null)
                return extension;
        }
        return null;
    }
}
