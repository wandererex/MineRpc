package me.stevenkin.minerpc.spi.impl;

import me.stevenkin.minerpc.spi.extension.SimpleExt;

public class SimpleExtImpl2 implements SimpleExt {
    @Override
    public void hello() {
        System.out.println("hello impl2");
    }
}
