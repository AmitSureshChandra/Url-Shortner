package com.github.amitsureshchandra.urlshortner.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class SecurityConfig: WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors()
            .and()
            .authorizeRequests().antMatchers("/api/**").authenticated()
            .and()
            .authorizeRequests().antMatchers("/**").permitAll()
            .and()
            .oauth2ResourceServer()
            .jwt()
    }
}