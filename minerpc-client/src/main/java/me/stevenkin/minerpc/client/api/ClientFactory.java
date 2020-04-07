package me.stevenkin.minerpc.client.api;

import me.stevenkin.minerpc.common.URL;
import me.stevenkin.minerpc.spi.Adaptive;
import me.stevenkin.minerpc.spi.Spi;

@Spi("okhttp")
public interface ClientFactory {
    @Adaptive("client")
    Client getClient(URL url);
}
