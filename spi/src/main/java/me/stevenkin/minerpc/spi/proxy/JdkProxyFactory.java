package me.stevenkin.minerpc.spi.proxy;

import me.stevenkin.minerpc.common.URL;
import me.stevenkin.minerpc.spi.Adaptive;
import me.stevenkin.minerpc.spi.ExtensionLoader;
import me.stevenkin.minerpc.spi.ProxyFactory;
import me.stevenkin.minerpc.spi.Spi;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyFactory implements ProxyFactory {
    @Override
    public <T> T getAdaptiveExtension(Class<T> spiClass) {
        if (!spiClass.isAnnotationPresent(Spi.class))
            throw new IllegalArgumentException("spi class must have spi annotation");
        return (T) Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(), new Class[]{spiClass}, (p, m, args) -> {
            if (!m.isAnnotationPresent(Adaptive.class))
                throw new RuntimeException("this method" + m + "can not be invoked");
            String[] values = m.getDeclaredAnnotation(Adaptive.class).value();
            Class<?>[] types = m.getParameterTypes();
            URL url1 = null;
            int i = 0;
            for (Class<?> type : types) {
                if (type.equals(URL.class)) {
                    url1 = (URL) args[i];
                    break;
                }
                Method method1 = null;
                for (Method method : type.getMethods()) {
                    if (method.getParameterTypes().length == 0 && method.getReturnType().equals(URL.class)){
                        method1 = method;
                        break;
                    }
                }
                if (method1 != null) {
                    url1 = (URL) method1.invoke(args[i], new Object[0]);
                    break;
                }
                i++;
            }
            if (url1 == null)
                throw new IllegalArgumentException("url must not be null");
            String extName = null;
            for (String value : values) {
                if (url1.getParameters().containsKey(value)) {
                    extName = url1.getParameters().get(value);
                }
            }
            String spiExtName = null;
            if (extName == null && (((spiExtName = spiClass.getAnnotation(Spi.class).value()) == null) || spiExtName.equals(""))) {
                throw new IllegalStateException("not ext name");
            }
            if (extName == null)
                extName = spiExtName;
            return m.invoke(ExtensionLoader.getExtensionLoader(spiClass).getExtension(extName), args);
        });
    }
}
