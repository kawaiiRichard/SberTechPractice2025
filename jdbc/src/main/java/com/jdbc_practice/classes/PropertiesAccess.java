package com.jdbc_practice.classes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesAccess {
    private String jdbcURL;
    private String username;
    private String password;
    private String configsPath;

    private Properties props = new Properties();

    public PropertiesAccess () {

    }

    public PropertiesAccess(String configsPath) {
        this.configsPath = configsPath;
        loadProperties();
    }
    
    public void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configsPath)) {
            if (input == null) {
                throw new RuntimeException("Файл не найден: " + configsPath);
            }            
            props.load(input);

            // jdbcURL = props.getProperty("jdbcURL");
            // username = props.getProperty("username");
            // password = props.getProperty("password");

            jdbcURL = validateProperty(props.getProperty("jdbcURL"), "jdbcURL");
            username = validateProperty(props.getProperty("username"), "username");
            password = validateProperty(props.getProperty("password"), "password");

            System.out.println("Файл успешно прочитан.");
        } catch (IOException ex) {
            throw new RuntimeException("Ошибка загрузки свойств", ex);
        }
    }

    private String validateProperty(String value, String ex) {
        if (value != null && !value.matches("^[\\w:\\/~\\-\\.@]*$")) { 
            throw new RuntimeException("Некорректное значение для свойства: " + ex);
        }
        return value;
    }

    public String getJdbcURL() {
        return jdbcURL;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
