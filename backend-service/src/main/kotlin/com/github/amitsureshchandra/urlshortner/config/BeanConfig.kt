package com.github.amitsureshchandra.urlshortner.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class BeanConfig {

    @Bean
    fun restTemplate(): RestTemplate{
        return RestTemplate();
    }

    @Bean
    fun objectMapper(): ObjectMapper{
        return ObjectMapper();
    }
}