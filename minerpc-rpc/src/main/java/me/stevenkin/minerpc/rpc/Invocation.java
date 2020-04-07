package me.stevenkin.minerpc.rpc;

import java.util.Map;

public interface Invocation {
    String getMethodName();

    Class<?>[] getParameterTypes();

    Object[] getArguments();

    Map<String, String> getAttachments();

    Invoker getInvoker();

}