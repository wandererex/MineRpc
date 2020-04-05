package me.stevenkin.minerpc.spi;

import me.stevenkin.minerpc.common.URL;

@Spi("jdk")
public interface ProxyFactory {

    <T> T getAdaptiveProxy(Class<T> spiClass);

}
