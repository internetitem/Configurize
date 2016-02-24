package com.internetitem.config.web;

import com.internetitem.config.ConfigManager;
import com.internetitem.config.dataModel.ConfigEntry;
import com.internetitem.config.dataModel.web.GetConfigResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ConfigWebService {

    private static final java.lang.String CRITERIA_PREFIX = "criteria.";

    private ConfigManager configManager;

    @Autowired
    public ConfigWebService(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @RequestMapping(path = "/config/{applicationName}", method = RequestMethod.GET)
    public GetConfigResponse getConfigs(@PathVariable("applicationName") String applicationName, @RequestParam(value = "component", required = false) List<String> componentNames, @RequestParam Map<String, String> allParams) {
        Map<String, String> criteria = extractCriteria(allParams);
        if (componentNames == null) {
            componentNames = Collections.emptyList();
        }
        List<ConfigEntry> configs = configManager.getConfigs(applicationName, componentNames, criteria);
        return new GetConfigResponse(true, "Success", configs);
    }

    private Map<String, String> extractCriteria(Map<String, String> params) {
        return params.entrySet().stream()
                .filter(e -> e.getKey().startsWith(CRITERIA_PREFIX))
                .collect(Collectors.toMap(
                        e -> e.getKey().substring(CRITERIA_PREFIX.length()),
                        e-> e.getValue()));
    }

}
