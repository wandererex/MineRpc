package me.stevenkin.minerpc.spi.impl;

import me.stevenkin.minerpc.spi.extension.SimpleExt;

public class SimpleExtImpl1 implements SimpleExt {
    @Override
    public void hello() {
        System.out.println("hello impl1");
    }
}
