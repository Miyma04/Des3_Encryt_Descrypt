package com.encryptiontool;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ConfigReader {
    private static final String CONFIG_PATH = "config.properties";

    public String getEncryptionKey() {
        return getProperty("encryption.key");
    }

    public List<Integer> getFieldsToEncrypt() {
        String fields = getProperty("fields.to.encrypt");
        return Arrays.stream(fields.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private String getProperty(String key) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(Path.of(CONFIG_PATH).toFile())) {
            properties.load(input);
            return properties.getProperty(key, "");
        } catch (IOException e) {
            System.err.println("Ошибка загрузки конфигурации: " + e.getMessage());
            return "";
        }
    }
}
