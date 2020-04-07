package me.stevenkin.minerpc.spi;

import me.stevenkin.minerpc.spi.extension.SimpleExt;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ExtensionLoaderTest {
    @Test
    public void testSimpleExtLoaderNotNull() {
        assertTrue(ExtensionLoader.getExtensionLoader(SimpleExt.class) != null);
    }

    @Test
    public void testAdaptive() {
        ExtensionLoader.getExtensionLoader(SimpleExt.class).getAdaptiveExtension().hello();
    }

    @Test
    public void testImpl1() {
        ExtensionLoader.getExtensionLoader(SimpleExt.class).getExtension("impl1").hello();
    }

    @Test
    public void testImpl2() {
        ExtensionLoader.getExtensionLoader(SimpleExt.class).getExtension("impl2").hello();
    }
}
