package me.stevenkin.minerpc.server.api;

import me.stevenkin.minerpc.common.URL;
import me.stevenkin.minerpc.spi.Adaptive;
import me.stevenkin.minerpc.spi.Spi;

@Spi("tomcat")
public interface ServerFactory {
    @Adaptive("server")
    Server getServer(URL url);
}
