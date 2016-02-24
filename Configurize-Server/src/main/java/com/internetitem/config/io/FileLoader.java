package com.internetitem.config.io;

import java.io.IOException;
import java.io.Reader;

public interface FileLoader {

    void loadFile(String name, Reader reader) throws IOException;

}
