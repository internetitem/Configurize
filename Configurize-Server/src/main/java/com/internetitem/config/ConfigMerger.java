package com.internetitem.config;

import com.internetitem.config.dataModel.ConfigEntry;
import com.internetitem.config.dataModel.ConfigFile;

import java.util.*;
import java.util.stream.Collectors;

public class ConfigMerger {

    public List<ConfigEntry> mergeConfigs(List<ConfigFile> applicationConfigs, List<List<ConfigFile>> componentConfigs) {
        Map<String, ConfigEntry> entryMap = new HashMap<>();
        addConfigsToMap(entryMap, applicationConfigs);
        for (List<ConfigFile> componentConfigList : componentConfigs) {
            addConfigsToMap(entryMap, componentConfigList);
        }
        return new ArrayList<>(entryMap.values());
    }

    private void addConfigsToMap(Map<String, ConfigEntry> map, List<ConfigFile> configs) {
        List<ConfigFile> sorted = sortConfigs(configs);
        for (ConfigFile config : sorted) {
            addConfigToMap(map, config);
        }
    }

    public List<ConfigFile> sortConfigs(List<ConfigFile> configs) {
        return configs.stream().sorted(
                Comparator.comparingInt(ConfigFile::getPriority).
                        thenComparingInt(f -> f.getCriteria().size()).reversed()
        ).collect(Collectors.toList());
    }

    private void addConfigToMap(Map<String, ConfigEntry> map, ConfigFile config) {
        for (Map.Entry<String, String> entry : config.getValues().entrySet()) {
            addEntryIfNotPresent(map, entry.getKey(), entry.getValue(), config.getName());
        }
    }

    private void addEntryIfNotPresent(Map<String, ConfigEntry> map, String key, String value, String name) {
        map.computeIfAbsent(key, k -> new ConfigEntry(key, value, name));
    }

}
