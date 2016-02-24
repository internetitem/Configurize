package com.internetitem.config;

import com.internetitem.config.dataModel.ConfigFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ConfigFileCache {

    private static Logger logger = LoggerFactory.getLogger(ConfigFileCache.class);

    private Map<String, List<ConfigFile>> applicationMap = new HashMap<>();
    private Map<String, List<ConfigFile>> componentMap = new HashMap<>();

    public void addConfigFile(ConfigFile configFile) {
        if (isApplication(configFile)) {
            logger.debug("Adding application {} from file {}", configFile.getApplication(), configFile.getName());
            addToMap(applicationMap, configFile.getApplication(), configFile);
        } else if (isComponent(configFile)) {
            logger.debug("Adding component {} from file {}", configFile.getApplication(), configFile.getName());
            addToMap(componentMap, configFile.getComponent(), configFile);
        } else {
            throw new RuntimeException("Unknown ConfigFile type");
        }
    }

    private boolean isApplication(ConfigFile configFile) {
        return configFile.getApplication() != null;
    }

    private boolean isComponent(ConfigFile configFile) {
        return configFile.getComponent() != null;
    }

    private void addToMap(Map<String, List<ConfigFile>> map, String key, ConfigFile configFile) {
        List<ConfigFile> list = map.computeIfAbsent(key, v -> new ArrayList<>());
        list.add(configFile);
    }

    public List<ConfigFile> getApplicationConfigs(String applicationName) {
        return applicationMap.getOrDefault(applicationName, Collections.emptyList());
    }

    public List<ConfigFile> getComponentConfigs(String componentName) {
        return componentMap.getOrDefault(componentName, Collections.emptyList());
    }
}
