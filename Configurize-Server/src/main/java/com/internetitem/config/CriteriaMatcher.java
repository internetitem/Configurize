package com.internetitem.config;

import com.internetitem.config.dataModel.ConfigFile;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CriteriaMatcher {

    public boolean matches(Map<String, String> input, Map<String, Set<String>> criteria) {
        return criteria.entrySet().stream().allMatch(criteriaEntry ->
                input.containsKey(criteriaEntry.getKey()) &&
                        criteriaEntry.getValue().contains(input.get(criteriaEntry.getKey()))
        );
    }

    public boolean matches(Map<String, String> input, ConfigFile configFile) {
        return matches(input, configFile.getCriteria());
    }

    public List<ConfigFile> getMatchingConfigFiles(Map<String, String> input, List<ConfigFile> configFiles) {
        return configFiles.stream().filter(
                configFile -> matches(input, configFile)
        ).collect(Collectors.toList());
    }
}
