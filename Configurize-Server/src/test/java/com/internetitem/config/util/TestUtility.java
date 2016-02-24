package com.internetitem.config.util;

import com.internetitem.config.dataModel.ConfigEntry;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TestUtility {

    public static Map<String, ConfigEntry> entryListToMap(List<ConfigEntry> entries) {
        return entries.stream().collect(Collectors.toMap(ConfigEntry::getKey, e -> e));
    }


    public static void assertEntryMapContains(Map<String, ConfigEntry> map, String key, String expectedValue) {
        ConfigEntry entry = map.get(key);
        assertNotNull("contains key " + key, entry);
        assertEquals("key " + key, expectedValue, entry.getValue());
    }

    public static void assertEntryMapDoesNotContain(Map<String, ConfigEntry> map, String key) {
        ConfigEntry entry = map.get(key);
        assertNull("does not contain key " + key, entry);
    }

}
