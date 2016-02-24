package com.internetitem.config;

import com.internetitem.config.dataModel.ConfigEntry;
import com.internetitem.config.dataModel.ConfigFile;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.internetitem.config.util.TestUtility.assertEntryMapContains;
import static com.internetitem.config.util.TestUtility.entryListToMap;
import static org.junit.Assert.assertSame;

public class ConfigMergerTest {

    @Test
    public void testMergeConfigs() throws Exception {
        List<ConfigFile> applicationConfigs = new ArrayList<>();

        ConfigFile app1 = ConfigFile.createApplicationConfig("app1.txt", 1, "app1");
        app1.getValues().put("k1", "v1");
        app1.getValues().put("k2", "v2");
        applicationConfigs.add(app1);

        ConfigFile app2 = ConfigFile.createApplicationConfig("app2.txt", 0, "app1");
        app2.getValues().put("k1", "v3");
        app2.getValues().put("k4", "v4");
        applicationConfigs.add(app2);

        List<List<ConfigFile>> componentConfigs = new ArrayList<>();

        List<ConfigFile> compList1 = new ArrayList<>();
        ConfigFile comp1 = ConfigFile.createComponentConfig("comp1.txt", 1, "comp1");
        comp1.getValues().put("k5", "v5");
        comp1.getValues().put("k4", "v6");
        compList1.add(comp1);

        ConfigFile comp2 = ConfigFile.createComponentConfig("comp2.txt", 2, "comp2");
        comp2.getValues().put("k5", "v5");
        comp2.getValues().put("k4", "v6");
        compList1.add(comp2);

        List<ConfigFile> compList2 = new ArrayList<>();
        ConfigFile comp3 = ConfigFile.createComponentConfig("comp3.txt", 1, "comp3");
        comp3.getValues().put("k5", "v5");
        comp3.getValues().put("k4", "v6");
        compList2.add(comp3);

        componentConfigs.add(compList1);
        componentConfigs.add(compList2);

        List<ConfigEntry> entries = new ConfigMerger().mergeConfigs(applicationConfigs, componentConfigs);
        Map<String, ConfigEntry> entryMap = entryListToMap(entries);
        assertEntryMapContains(entryMap, "k1", "v1");
        assertEntryMapContains(entryMap, "k2", "v2");
        assertEntryMapContains(entryMap, "k4", "v4");
        assertEntryMapContains(entryMap, "k5", "v5");
    }


    @Test
    public void testSortConfigsByPriority() throws Exception {
        List<ConfigFile> list = new ArrayList<>();
        ConfigFile file1 = ConfigFile.createApplicationConfig("app10", 10, "app1");
        list.add(file1);
        ConfigFile file2 = ConfigFile.createApplicationConfig("app11", 11, "app1");
        list.add(file2);
        ConfigFile file3 = ConfigFile.createApplicationConfig("app9", 9, "app1");
        list.add(file3);
        List<ConfigFile> sorted = new ConfigMerger().sortConfigs(list);
        assertSame(file2, sorted.get(0));
        assertSame(file1, sorted.get(1));
        assertSame(file3, sorted.get(2));
    }

    @Test
    public void testSortConfigsByCriteria() throws Exception {
        List<ConfigFile> list = new ArrayList<>();
        ConfigFile file1 = ConfigFile.createApplicationConfig("app1", 0, "app1");
        file1.getCriteria().put("c1", Collections.singleton("v1"));
        list.add(file1);
        ConfigFile file2 = ConfigFile.createApplicationConfig("app0", 0, "app1");
        list.add(file2);
        ConfigFile file3 = ConfigFile.createApplicationConfig("app2", 0, "app1");
        file3.getCriteria().put("c1", Collections.singleton("v1"));
        file3.getCriteria().put("c2", Collections.singleton("v2"));
        list.add(file3);
        List<ConfigFile> sorted = new ConfigMerger().sortConfigs(list);
        assertSame(file3, sorted.get(0));
        assertSame(file1, sorted.get(1));
        assertSame(file2, sorted.get(2));
    }

    @Test
    public void testSortConfigsByPriorityAndCriteria() throws Exception {
        List<ConfigFile> list = new ArrayList<>();

        ConfigFile file1 = ConfigFile.createApplicationConfig("app10-2", 10, "app1");
        file1.getCriteria().put("c1", Collections.singleton("v1"));
        file1.getCriteria().put("c2", Collections.singleton("v2"));
        list.add(file1);

        ConfigFile file2 = ConfigFile.createApplicationConfig("app9-0", 9, "app1");
        list.add(file2);

        ConfigFile file3 = ConfigFile.createApplicationConfig("app8-0", 8, "app1");
        list.add(file3);

        ConfigFile file4 = ConfigFile.createApplicationConfig("app11-0", 11, "app1");
        list.add(file4);

        ConfigFile file5 = ConfigFile.createApplicationConfig("app8-1", 8, "app1");
        file5.getCriteria().put("c1", Collections.singleton("v1"));
        list.add(file5);

        ConfigFile file6 = ConfigFile.createApplicationConfig("app12-1", 12, "app1");
        file6.getCriteria().put("c1", Collections.singleton("v1"));
        list.add(file6);

        ConfigFile file7 = ConfigFile.createApplicationConfig("app5-1", 5, "app1");
        file7.getCriteria().put("c1", Collections.singleton("v1"));
        list.add(file7);

        ConfigFile file8 = ConfigFile.createApplicationConfig("app10-1", 10, "app1");
        file8.getCriteria().put("c1", Collections.singleton("v1"));
        list.add(file8);

        List<ConfigFile> sorted = new ConfigMerger().sortConfigs(list);
        assertSame(file6, sorted.get(0));
        assertSame(file4, sorted.get(1));
        assertSame(file1, sorted.get(2));
        assertSame(file8, sorted.get(3));
        assertSame(file2, sorted.get(4));
        assertSame(file5, sorted.get(5));
        assertSame(file3, sorted.get(6));
        assertSame(file7, sorted.get(7));
    }
}