package com.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.util.Properties;

@Getter
@Configuration
public class PropConfig {

    @Value("${user.cache.size}")
    private String size;

    public String getCacheSize1() {
        FileReader reader= null;
        Properties p=new Properties();
        try {
            reader = new FileReader("src/main/resources/application.yml");
            p.load(reader);
            reader.close();
        } catch (Exception e) {
            System.out.println("Error while Reading Properties file!!!");
            e.printStackTrace();
            return "4";
        }
        return p.getProperty("user.cache.size");
    }
}
