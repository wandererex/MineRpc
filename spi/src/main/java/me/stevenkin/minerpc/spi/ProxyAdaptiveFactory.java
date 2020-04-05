package me.stevenkin.minerpc.spi;


@Spi("jdk")
public interface ProxyAdaptiveFactory {

    <T> T getAdaptiveProxy(Class<T> spiClass);

}
