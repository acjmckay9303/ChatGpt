package com.example.gpt4.demo.configuration;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("css", "text/css");
        factory.setMimeMappings(mappings);
    }
}

