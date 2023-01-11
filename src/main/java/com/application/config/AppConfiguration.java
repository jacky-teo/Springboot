package com.application.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class AppConfiguration {
    @Value("${save.file.location}")
    private String saveFileLocation;
}
