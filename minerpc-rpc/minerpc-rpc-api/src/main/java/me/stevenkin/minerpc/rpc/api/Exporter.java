package me.stevenkin.minerpc.rpc.api;

public interface Exporter {
    Invoker getInvoker();

    void unexport();
}