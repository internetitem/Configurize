package com.internetitem.config.dataModel;

public class ConfigEntry {

    private String key;
    private String value;
    private String source;

    public ConfigEntry() {
    }

    public ConfigEntry(String key, String value, String source) {
        this.key = key;
        this.value = value;
        this.source = source;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
