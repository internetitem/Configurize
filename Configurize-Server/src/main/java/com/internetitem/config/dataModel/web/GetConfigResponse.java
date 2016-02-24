package com.internetitem.config.dataModel.web;

import com.internetitem.config.dataModel.ConfigEntry;

import java.util.List;

public class GetConfigResponse extends BasicResponse {

    private List<ConfigEntry> values;

    public GetConfigResponse(boolean success, String message, List<ConfigEntry> values) {
        super(success, message);
        this.values = values;
    }

    public List<ConfigEntry> getValues() {
        return values;
    }

    public void setValues(List<ConfigEntry> values) {
        this.values = values;
    }
}
