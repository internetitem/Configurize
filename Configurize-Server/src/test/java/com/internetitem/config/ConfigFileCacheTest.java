package com.internetitem.config;

import com.internetitem.config.dataModel.ConfigFile;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ConfigFileCacheTest {

    @Test
    public void testCacheTypes() throws Exception {
        ConfigFileCache cache = new ConfigFileCache();

        ConfigFile app1 = new ConfigFile();
        app1.setName("app1");
        app1.setApplication("App1");
        Map<String, String> app1Values = new HashMap<>();
        app1Values.put("k1", "v1");
        app1.setValues(app1Values);

        cache.addConfigFile(app1);
        
        ConfigFile comp1 = new ConfigFile();
        comp1.setName("comp1");
        comp1.setComponent("Comp1");
        Map<String, String> comp1Values = new HashMap<>();
        comp1Values.put("k2", "v2");
        comp1.setValues(comp1Values);

        cache.addConfigFile(comp1);

        List<ConfigFile> app1Configs = cache.getApplicationConfigs("App1");
        assertNotNull(app1Configs);
        assertEquals(1, app1Configs.size());
        assertSame(app1, app1Configs.get(0));

        List<ConfigFile> app2Configs = cache.getApplicationConfigs("App2");
        assertNotNull(app2Configs);
        assertEquals(0, app2Configs.size());

        List<ConfigFile> app1AsComp = cache.getComponentConfigs("App1");
        assertNotNull(app1AsComp);
        assertEquals(0, app1AsComp.size());

        List<ConfigFile> comp1Configs = cache.getComponentConfigs("Comp1");
        assertNotNull(comp1Configs);
        assertEquals(1, comp1Configs.size());
        assertSame(comp1, comp1Configs.get(0));

        List<ConfigFile> comp2Configs = cache.getComponentConfigs("Comp2");
        assertNotNull(comp2Configs);
        assertEquals(0, comp2Configs.size());

        List<ConfigFile> comp1AsApp = cache.getApplicationConfigs("Comp1");
        assertNotNull(comp1AsApp);
        assertEquals(0, comp1AsApp.size());
    }

    @Test(expected = RuntimeException.class)
    public void testUnknownConfigType() throws Exception {
        ConfigFileCache cache = new ConfigFileCache();
        cache.addConfigFile(new ConfigFile());
    }
}