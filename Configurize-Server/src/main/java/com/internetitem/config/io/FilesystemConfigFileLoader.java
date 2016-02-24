package com.internetitem.config.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FilesystemConfigFileLoader implements ConfigFileLoader {

    private static Logger logger = LoggerFactory.getLogger(FilesystemConfigFileLoader.class);

    @Override
    public void loadDirectory(FileLoader loader, String directoryName) throws IOException {
        File directory = new File(directoryName);
        if (!directory.isDirectory()) {
            throw new IOException("Path " + directoryName + " is not a directory");
        }

        processDirectory(loader, directory);
    }

    private void processDirectory(FileLoader loader, File directory) throws IOException {
        logger.debug("Processing directory {}", directory.getAbsolutePath());
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                processDirectory(loader, file);
            } else if (isValidFile(file)) {
                processFile(loader, file);
            }
        }
    }

    private boolean isValidFile(File file) {
        return file.getName().endsWith(".json");
    }

    private void processFile(FileLoader loader, File file) throws IOException {
        logger.info("Processing file {}", file.getAbsolutePath());
        try (FileInputStream fis = new FileInputStream(file)) {
            try (InputStreamReader reader = new InputStreamReader(fis, "UTF-8")) {
                loader.loadFile(file.getCanonicalPath(), reader);
            }
        }
    }

}
