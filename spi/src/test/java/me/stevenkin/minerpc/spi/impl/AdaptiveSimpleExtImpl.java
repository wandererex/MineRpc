package me.stevenkin.minerpc.spi.impl;

import me.stevenkin.minerpc.spi.Adaptive;
import me.stevenkin.minerpc.spi.extension.SimpleExt;

@Adaptive
public class AdaptiveSimpleExtImpl implements SimpleExt {
    @Override
    public void hello() {
        System.out.println("hello adaptive");
    }
}
