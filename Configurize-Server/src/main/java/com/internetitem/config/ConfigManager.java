package com.internetitem.config;

import com.internetitem.config.dataModel.ConfigEntry;
import com.internetitem.config.dataModel.ConfigFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private ConfigFileCache configFileCache;

    private ConfigMerger merger;
    private CriteriaMatcher matcher;

    public ConfigManager(ConfigFileCache configFileCache, ConfigMerger merger, CriteriaMatcher matcher) {
        this.configFileCache = configFileCache;
        this.merger = merger;
        this.matcher = matcher;
    }

    public List<ConfigEntry> getConfigs(String applicationName, List<String> componentNames, Map<String, String> criteria) {
        List<ConfigFile> applicationConfigs = matcher.getMatchingConfigFiles(criteria, configFileCache.getApplicationConfigs(applicationName));
        List<List<ConfigFile>> componentConfigs = new ArrayList<>();
        for (String componentName : componentNames) {
            componentConfigs.add(matcher.getMatchingConfigFiles(criteria, configFileCache.getComponentConfigs(componentName)));
        }
        return merger.mergeConfigs(applicationConfigs, componentConfigs);
    }

}
