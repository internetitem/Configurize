package com.internetitem.config;

import com.internetitem.config.dataModel.ConfigEntry;
import com.internetitem.config.dataModel.ConfigFile;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static com.internetitem.config.util.TestUtility.*;

public class ConfigManagerTest {

    private ConfigFileCache configFileCache = new ConfigFileCache();
    private ConfigMerger merger = new ConfigMerger();
    private CriteriaMatcher matcher = new CriteriaMatcher();
    private ConfigManager configManager = new ConfigManager(configFileCache, merger, matcher);

    @Before
    public void setUp() throws Exception {
        ConfigFile app1_1 = ConfigFile.createApplicationConfig("app1-1.txt", 1, "App1");
        app1_1.getValues().put("k0", "v0");
        app1_1.getValues().put("k2", "v2");
        configFileCache.addConfigFile(app1_1);

        ConfigFile app1_2 = ConfigFile.createApplicationConfig("app1-2.txt", 2, "App1");
        app1_2.getValues().put("k1", "v3");
        app1_2.getValues().put("k2", "v4");
        app1_2.getValues().put("k3", "v3-yes");
        configFileCache.addConfigFile(app1_2);

        ConfigFile app1_3 = ConfigFile.createApplicationConfig("app1-3.txt", 3, "App1");
        app1_3.getValues().put("k1", "v5");
        app1_3.getValues().put("k2", "v6");
        app1_3.getValues().put("k4", "k4-no");
        app1_3.getCriteria().put("Environment", Collections.singleton("Prod"));
        app1_3.getCriteria().put("Datacenter", Collections.singleton("D1"));
        configFileCache.addConfigFile(app1_3);

        ConfigFile app2_1 = ConfigFile.createApplicationConfig("app2-1.txt", 4, "App2");
        app2_1.getValues().put("k5", "v5-no");
        configFileCache.addConfigFile(app2_1);

        ConfigFile comp1_1 = ConfigFile.createComponentConfig("comp1-1.txt", 10, "Comp1");
        comp1_1.getValues().put("k0", "v0-no");
        comp1_1.getValues().put("KComp1", "VComp1");
        comp1_1.getCriteria().put("Datacenter", Collections.singleton("D2"));
        configFileCache.addConfigFile(comp1_1);

        ConfigFile comp1_2 = ConfigFile.createComponentConfig("comp1-2.txt", 5, "Comp1");
        comp1_2.getValues().put("k0", "v0-no");
        comp1_2.getValues().put("KComp1", "VComp1-no");
        comp1_2.getValues().put("KComp2", "VComp2");
        comp1_2.getCriteria().put("Datacenter", Collections.singleton("D2"));
        configFileCache.addConfigFile(comp1_2);
    }

    @Test
    public void testGetConfigs() throws Exception {
        Map<String, String> criteria = new HashMap<>();
        criteria.put("Environment", "Prod");
        criteria.put("Datacenter", "D2");
        criteria.put("Version", "V1");

        List<String> components = new ArrayList<>();
        components.add("Comp1");
        components.add("Comp2");

        List<ConfigEntry> entries = configManager.getConfigs("App1", components, criteria);
        Map<String, ConfigEntry> entryMap = entryListToMap(entries);

        assertEntryMapContains(entryMap, "k0", "v0");
        assertEntryMapContains(entryMap, "k1", "v3");
        assertEntryMapContains(entryMap, "k2", "v4");
        assertEntryMapContains(entryMap, "k3", "v3-yes");
        assertEntryMapContains(entryMap, "KComp1", "VComp1");
        assertEntryMapContains(entryMap, "KComp2", "VComp2");
        assertEntryMapDoesNotContain(entryMap, "k4");
        assertEntryMapDoesNotContain(entryMap, "k5");
    }
}