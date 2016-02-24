package com.internetitem.config.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internetitem.config.ConfigFileCache;
import com.internetitem.config.dataModel.ConfigFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;

public class FileLoaderImpl implements FileLoader {

    private static Logger logger = LoggerFactory.getLogger(FileLoaderImpl.class);

    private ConfigFileCache configFileCache;
    private ObjectMapper objectMapper;

    public FileLoaderImpl(ConfigFileCache configFileCache, ObjectMapper objectMapper) {
        this.configFileCache = configFileCache;
        this.objectMapper = objectMapper;
    }

    @Override
    public void loadFile(String name, Reader reader) throws IOException {
        ConfigFile configFile = objectMapper.readValue(reader, ConfigFile.class);
        configFile.setName(name);
        logger.debug("Adding file {} to cache", name);
        configFileCache.addConfigFile(configFile);
    }
}
