package me.stevenkin.minerpc.spi;

import me.stevenkin.minerpc.common.URL;

@Spi("jdk")
public interface ProxyFactory {

    <T> T getAdaptiveExtension(Class<T> spiClass);

}
