package com.encryptiontool;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties = new Properties();

    static {
        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Ошибка загрузки файла конфигурации: " + e.getMessage());
        }
    }

    public static String getKey() {
        return properties.getProperty("key");
    }

    public static String getEncryptFields() {
        return properties.getProperty("encryptFields");
    }
}
