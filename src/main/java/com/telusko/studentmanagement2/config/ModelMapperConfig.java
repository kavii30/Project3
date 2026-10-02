package com.telusko.studentmanagement2.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class ModelMapperConfig {
    @Bean
    ModelMapper modelmapper(){
        return new ModelMapper();
    }
}
