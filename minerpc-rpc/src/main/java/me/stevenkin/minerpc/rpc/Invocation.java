package me.stevenkin.minerpc.rpc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Invocation {
    private final Class serviceClass;

    private final Method methodName;

    private final Class[] paramTypes;

    private final Object[] args;

    private final Map<String, Object> attachs;

    private final Invoker invoker;

    public Invocation(Class serviceClass, Method methodName, Class[] paramTypes, Object[] args, Map<String, Object> attachs, Invoker invoker) {
        this.serviceClass = serviceClass;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.args = args;
        this.attachs = attachs;
        this.invoker = invoker;
    }

    public Class getServiceClass() {
        return serviceClass;
    }

    public Method getMethodName() {
        return methodName;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public Map<String, Object> getAttachs() {
        return new HashMap<>(attachs);
    }

    public Invoker getInvoker() {
        return invoker;
    }
}