package com.internetitem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internetitem.config.io.ConfigFileLoader;
import com.internetitem.config.io.FileLoader;
import com.internetitem.config.io.FileLoaderImpl;
import com.internetitem.config.io.FilesystemConfigFileLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
@ComponentScan
@Import(
        {
                ServerPropertiesAutoConfiguration.class,
                EmbeddedServletContainerAutoConfiguration.class,
                HttpMessageConvertersAutoConfiguration.class,
                JacksonAutoConfiguration.class,
                WebMvcAutoConfiguration.class,
                ErrorMvcAutoConfiguration.class,
                DispatcherServletAutoConfiguration.class,
                PropertyPlaceholderAutoConfiguration.class,
                JmxAutoConfiguration.class,
        }
)
public class ConfigServiceConfiguration {

    @Value("${searchDirectory}")
    private String searchDirectory;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void addSearchDirectories() throws IOException {
        configFileLoader().loadDirectory(fileLoader(), searchDirectory);
    }

    @Bean
    public ConfigFileCache configFileCache() {
        return new ConfigFileCache();
    }

    @Bean
    public ConfigFileLoader configFileLoader() {
        return new FilesystemConfigFileLoader();
    }

    @Bean
    public FileLoader fileLoader() {
        return new FileLoaderImpl(configFileCache(), objectMapper);
    }

    @Bean
    public ConfigMerger configMerger() {
        return new ConfigMerger();
    }

    @Bean
    public CriteriaMatcher criteriaMatcher() {
        return new CriteriaMatcher();
    }

    @Bean
    public ConfigManager configManager() {
        return new ConfigManager(configFileCache(), configMerger(), criteriaMatcher());
    }

}
