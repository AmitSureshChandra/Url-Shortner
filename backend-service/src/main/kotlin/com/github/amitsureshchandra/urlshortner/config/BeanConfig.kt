package com.github.amitsureshchandra.urlshortner.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.config.annotation.CorsRegistry

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class BeanConfig {

    @Value("\${frontend-url}")
    var frontendUrl: String? = null;

    @Bean
    fun restTemplate(): RestTemplate{
        return RestTemplate();
    }

    @Bean
    fun objectMapper(): ObjectMapper{
        return ObjectMapper();
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer? {
        println(frontendUrl)
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**").allowedOrigins(frontendUrl)
            }
        }
    }
}