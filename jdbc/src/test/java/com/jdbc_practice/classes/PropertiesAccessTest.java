package com.jdbc_practice.classes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class PropertiesAccessTest {
    @Test
    public void test_loadPropertiesSuccess() {
        PropertiesAccess propsAccess = new PropertiesAccess("config.properties");
        assertEquals("jdbc:h2:~/test_db", propsAccess.getJdbcURL());
        assertEquals("sa", propsAccess.getUsername());
        assertEquals("", propsAccess.getPassword());
    }

    @Test(expected = RuntimeException.class)
    public void test_loadPropertiesFileNotFound() {
        new PropertiesAccess("nonexistent.properties");
    }

    @Test(expected = RuntimeException.class)
    public void test_loadPropertiesCorruptedFile() {
        new PropertiesAccess("corrupted.properties"); 
    }

    @Test
    public void test_getters() {
        PropertiesAccess propsAccess = new PropertiesAccess("config.properties");
        assertNotNull(propsAccess.getJdbcURL());
        assertNotNull(propsAccess.getUsername());
        assertNotNull(propsAccess.getPassword());
    }
}
