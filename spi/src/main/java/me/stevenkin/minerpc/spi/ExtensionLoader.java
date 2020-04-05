package me.stevenkin.minerpc.spi;

import me.stevenkin.minerpc.common.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class ExtensionLoader<T> {
    private static final Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);

    private static final String MINERPC_DIRECTORY = "META-INF/minerpc/";

    private static Map<Class<?>, ExtensionLoader<?>> extensionLoaderCache = new HashMap<>();

    private Class<T> type;

    private ExtensionFactory extensionFactory;

    private Map<String, Class<?>> extensionClassCache = new HashMap<>();

    private Map<Class<?>, Object> extensionInstanceCache = new HashMap<>();

    private Class<?> adaptiveClass;

    private T adaptiveInstance;

    private Set<String> nameCache = new HashSet<>();

    private Map<String, Class<?>> activateCache = new HashMap<>();

    private Set<Class<?>> wrapperCache = new HashSet<>();

    private boolean classesLoaded = false;

    private ExtensionLoader(Class<T> type) {
        this.type = type;
        this.extensionFactory = ExtensionFactory.class.equals(type) ? null : ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension();
    }

    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("type cant not be null");
        }
        if (!type.isInterface() || !type.isAnnotationPresent(Spi.class)) {
            throw new IllegalArgumentException("type must has spi annotation");
        }
        ExtensionLoader<?> loader;
        synchronized (ExtensionLoader.class) {
            loader = extensionLoaderCache.get(type);
            if (loader == null) {
                loader = new ExtensionLoader<>(type);
                extensionLoaderCache.put(type, loader);
            }
        }
        return (ExtensionLoader<T>) loader;
    }

    public synchronized T getExtension(String name) {
        loadExtensionClasses();
        Class<?> extensionClass = extensionClassCache.get(name);
        if (extensionClass == null)
            return null;
        return (T) extensionInstanceCache.computeIfAbsent(extensionClass, this::createExtension);
    }

    private T createExtension(Class<?> e) {
        try {
            Object instance = e.newInstance();
            instance = injectExtension(instance);
            if (wrapperCache != null && !wrapperCache.isEmpty()) {
                for (Class<?> clazz : wrapperCache) {
                    instance = injectExtension(clazz.getConstructor(type).newInstance(instance));
                }
                return (T) instance;
            }
            return (T) instance;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Object injectExtension(Object instance) {
        if (extensionFactory != null) {
            for (Method method : instance.getClass().getMethods()) {
                if (method.getName().startsWith("set") && method.getName().length() > 3 && method.getParameterTypes().length == 1 && Modifier.isPublic(method.getModifiers())) {
                    try {
                        method.invoke(instance, ExtensionLoader.getExtensionLoader(method.getParameterTypes()[0]).getExtension(method.getName().substring(3)));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return instance;
    }


    private void loadExtensionClasses() {
        if (classesLoaded)
            return;
        loadDirectory(MINERPC_DIRECTORY);
    }

    private void loadDirectory(String dir) {
        String path = dir + type.getName();
        try {
            Enumeration<java.net.URL> urls;
            ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
            if (classLoader != null) {
                urls = classLoader.getResources(path);
            } else {
                urls = ClassLoader.getSystemResources(path);
            }
            if (urls != null) {
                while (urls.hasMoreElements()) {
                    java.net.URL resourceURL = urls.nextElement();
                    loadResource(classLoader, resourceURL);
                }
            }
        } catch (Throwable t) {
            logger.error("Exception when load extension class(interface: " +
                    type + ", description file: " + path + ").", t);
        }
    }

    private void loadResource(ClassLoader classLoader, java.net.URL resourceURL) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceURL.openStream(), "utf-8"));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    final int ci = line.indexOf('#');
                    if (ci >= 0) line = line.substring(0, ci);
                    line = line.trim();
                    if (line.length() > 0) {
                        try {
                            String name = null;
                            int i = line.indexOf('=');
                            if (i > 0) {
                                name = line.substring(0, i).trim();
                                line = line.substring(i + 1).trim();
                            }
                            if (line.length() > 0) {
                                loadClass(resourceURL, Class.forName(line, true, classLoader), name);
                            }
                        } catch (Throwable t) {
                            IllegalStateException e = new IllegalStateException("Failed to load extension class(interface: " + type + ", class line: " + line + ") in " + resourceURL + ", cause: " + t.getMessage(), t);
                            logger.error("load extension class error {}", e);
                        }
                    }
                }
            } finally {
                reader.close();
            }
        } catch (Throwable t) {
            logger.error("Exception when load extension class(interface: " +
                    type + ", class file: " + resourceURL + ") in " + resourceURL, t);
        }
    }

    private void loadClass(java.net.URL url, Class<?> clazz, String name) {
        if (!type.isAssignableFrom(clazz))
            throw new IllegalArgumentException("extension class is not subclass of " + type);
        nameCache.add(name);
        if (clazz.isAnnotationPresent(Adaptive.class)) {
            if (adaptiveClass == null)
                adaptiveClass = clazz;
            else if (!adaptiveClass.equals(clazz))
                throw new IllegalArgumentException("adaptive class only one");
        } else if (isWrapperClass(clazz)) {
            wrapperCache.add(clazz);
        } else {
            extensionClassCache.putIfAbsent(name, clazz);
            if (clazz.isAnnotationPresent(Activate.class)) {
                activateCache.put(name, clazz);
            }
        }
    }

    private boolean isWrapperClass(Class<?> clazz) {
        try {
            clazz.getConstructor(type);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public synchronized T getAdaptiveExtension() {
        if (adaptiveInstance != null)
            return adaptiveInstance;
        loadExtensionClasses();
        if (adaptiveClass != null) {
            try {
                adaptiveInstance = (T) adaptiveClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            adaptiveInstance = createAdaptiveExtension();
        }
        return adaptiveInstance;
    }

    private T createAdaptiveExtension() {
        return ExtensionLoader.getExtensionLoader(ProxyAdaptiveFactory.class).getAdaptiveExtension().getAdaptiveProxy(type);
    }

    public synchronized List<T> getActivateExtension(URL url) {
        loadExtensionClasses();
        if (activateCache == null || activateCache.isEmpty())
            return new ArrayList<>();
        return activateCache.entrySet().stream()
                .filter(e -> match(e.getValue(), url))
                .sorted(Comparator.comparingInt(e -> e.getValue().getAnnotation(Activate.class).order()))
                .map(e -> getExtension(e.getKey()))
                .filter(e -> e != null)
                .collect(Collectors.toList());
    }

    private boolean match(Class<?> activateClass, URL url) {
        Activate activate = activateClass.getAnnotation(Activate.class);
        if (activate == null)
            throw new IllegalArgumentException("activate class must have Activate annotation");
        String[] values = activate.value();
        for (String value : values) {
            if (url.getParameters().containsKey(value))
                return true;
        }
        return false;
    }
}
