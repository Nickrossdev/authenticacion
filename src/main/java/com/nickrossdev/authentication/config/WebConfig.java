package com.nickrossdev.authentication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${app.uploads.path}")
    private String uploadsPath;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://192.168.1.111:5173")  // Asegúrate de que esta URL coincida con tu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/files/images/**")
                .addResourceLocations("file:" + uploadsPath + "/images");
        registry
                .addResourceHandler("/files/audio/**")
                .addResourceLocations("file:" + uploadsPath + "/audio");
        registry
                .addResourceHandler("/files/video/**")
                .addResourceLocations("file:" + uploadsPath + "/video");
    }
}
