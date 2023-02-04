package com.github.amitsureshchandra.urlshortner.controller

import com.github.amitsureshchandra.urlshortner.dto.LoginCredentials
import com.github.amitsureshchandra.urlshortner.dto.RegDto
import com.github.amitsureshchandra.urlshortner.repo.UserRepo
import com.github.amitsureshchandra.urlshortner.service.AuthService
import com.github.amitsureshchandra.urlshortner.service.util.JwtUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import kotlin.math.log

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(val authService: AuthService) {
    @PostMapping("/register")
    fun registerHandler(@RequestBody @Valid regDto: RegDto): MutableMap<String, String> {
        return authService.register(regDto)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginCredentials: LoginCredentials): MutableMap<String, String> {
        return authService.login(loginCredentials)
    }
}