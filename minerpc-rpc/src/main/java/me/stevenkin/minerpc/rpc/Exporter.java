package me.stevenkin.minerpc.rpc;

public interface Exporter {
    Invoker getInvoker();

    void unexport();
}