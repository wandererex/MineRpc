package me.stevenkin.minerpc.spi.proxy;

import me.stevenkin.minerpc.spi.Adaptive;
import me.stevenkin.minerpc.spi.ExtensionLoader;
import me.stevenkin.minerpc.spi.ProxyAdaptiveFactory;

@Adaptive
public class AdaptiveProxyAdaptiveFactory implements ProxyAdaptiveFactory {
    private static String defaultProxyType;

    public static void setDefaultProxyType(String defaultProxyType) {
        AdaptiveProxyAdaptiveFactory.defaultProxyType = defaultProxyType;
    }

    @Override
    public <T> T getAdaptiveProxy(Class<T> spiClass) {
        String proxyType = "jdk";
        if (defaultProxyType != null && !defaultProxyType.equals(""))
            proxyType = defaultProxyType;
        ProxyAdaptiveFactory proxyAdaptiveFactory = ExtensionLoader.getExtensionLoader(ProxyAdaptiveFactory.class).getExtension(proxyType);
        if (proxyAdaptiveFactory == null)
            throw new IllegalStateException("proxy factory must not be null");
        return proxyAdaptiveFactory.getAdaptiveProxy(spiClass);
    }
}
