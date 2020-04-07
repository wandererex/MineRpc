package me.stevenkin.minerpc.spring.extension;

import me.stevenkin.minerpc.spi.ExtensionFactory;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Set;

public class SpringExtensionFactory implements ExtensionFactory {
    private static final Set<ApplicationContext> contexts = new HashSet<>();

    public static void setContexts(ApplicationContext context) {
        contexts.add(context);
    }

    @Override
    public <T> T getExtension(Class<T> type, String name) {
        for (ApplicationContext context : contexts) {
            T t = context.getBean(name, type);
            if (t != null)
                return t;
        }
        return null;
    }
}
