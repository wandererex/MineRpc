package me.stevenkin.minerpc.spi;

@Spi
public interface ExtensionFactory {
    <T> T getExtension(Class<T> type, String name);
}
