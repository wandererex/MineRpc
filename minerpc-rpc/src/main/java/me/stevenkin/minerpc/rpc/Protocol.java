package me.stevenkin.minerpc.rpc;

import me.stevenkin.minerpc.common.URL;
import me.stevenkin.minerpc.spi.Adaptive;
import me.stevenkin.minerpc.spi.Spi;

@Spi("rest")
public interface Protocol {
    @Adaptive("protocol")
    Exporter export(Invoker invoker);

    @Adaptive("protocol")
    Invoker refer(URL url);

    void destroy();
}
