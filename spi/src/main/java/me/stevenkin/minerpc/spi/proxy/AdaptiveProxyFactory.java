package me.stevenkin.minerpc.spi.proxy;

import me.stevenkin.minerpc.spi.Adaptive;
import me.stevenkin.minerpc.spi.ExtensionLoader;
import me.stevenkin.minerpc.spi.ProxyFactory;

@Adaptive
public class AdaptiveProxyFactory implements ProxyFactory {
    private static String defaultProxyType;

    public static void setDefaultProxyType(String defaultProxyType) {
        AdaptiveProxyFactory.defaultProxyType = defaultProxyType;
    }

    @Override
    public <T> T getAdaptiveProxy(Class<T> spiClass) {
        String proxyType = "jdk";
        if (defaultProxyType != null && !defaultProxyType.equals(""))
            proxyType = defaultProxyType;
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getExtension(proxyType);
        if (proxyFactory == null)
            throw new IllegalStateException("proxy factory must not be null");
        return proxyFactory.getAdaptiveProxy(spiClass);
    }
}
