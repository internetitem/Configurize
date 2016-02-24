package com.internetitem.config.dataModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigFile {

    private String name;
    private int priority;

    private String application;
    private String component;

    private Map<String, Set<String>> criteria = new HashMap<>();

    private Map<String, String> values = new HashMap<>();

    public ConfigFile() {
    }

    private ConfigFile(String name, int priority, String application, String component) {
        this.name = name;
        this.priority = priority;
        this.application = application;
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Map<String, Set<String>> getCriteria() {
        return criteria;
    }

    public void setCriteria(Map<String, Set<String>> criteria) {
        this.criteria = criteria;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "ConfigFile{" +
                "name='" + name + '\'' +
                ", priority=" + priority +
                ", application='" + application + '\'' +
                ", component='" + component + '\'' +
                '}';
    }

    public static ConfigFile createApplicationConfig(String name, int priority, String application) {
        return new ConfigFile(name, priority, application, null);
    }

    public static ConfigFile createComponentConfig(String name, int priority, String component) {
        return new ConfigFile(name, priority, null, component);
    }
}
