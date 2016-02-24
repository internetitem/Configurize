package com.internetitem.config.io;

import java.io.IOException;

public interface ConfigFileLoader {

    void loadDirectory(FileLoader loader, String directory) throws IOException;

}
