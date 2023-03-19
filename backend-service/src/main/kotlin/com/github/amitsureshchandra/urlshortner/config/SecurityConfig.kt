package com.github.amitsureshchandra.urlshortner.config

import com.github.amitsureshchandra.urlshortner.filter.JwtFilter
import com.github.amitsureshchandra.urlshortner.repo.UserRepo
import com.github.amitsureshchandra.urlshortner.service.UrlUserDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
class SecurityConfig(val userRepo: UserRepo, val jwtFilter: JwtFilter, val urlUserDetailService: UrlUserDetailService): WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .httpBasic().disable()
            .cors()
//            .and().authorizeRequests().antMatchers("/h2-console/**").permitAll()
            .and().authorizeRequests().antMatchers("/api/v1/auth/register").permitAll()
            .and().authorizeRequests().antMatchers("/api/v1/auth/login").permitAll()
            .and().authorizeRequests().antMatchers("/api/v1/**").authenticated()
            .and().authorizeRequests().antMatchers("/**").permitAll()
            .and().userDetailsService(urlUserDetailService)
            .exceptionHandling()
            .authenticationEntryPoint { _: HttpServletRequest?, response: HttpServletResponse, _: AuthenticationException? ->
                response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Unauthorized"
                )
            }
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }
}