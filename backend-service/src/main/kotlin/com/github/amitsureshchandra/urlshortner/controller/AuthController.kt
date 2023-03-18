package com.github.amitsureshchandra.urlshortner.controller

import com.github.amitsureshchandra.urlshortner.dto.LoginCredentials
import com.github.amitsureshchandra.urlshortner.dto.RegDto
import com.github.amitsureshchandra.urlshortner.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(val authService: AuthService) {
    @PostMapping("/register")
    fun registerHandler(@Valid @RequestBody regDto: RegDto): ResponseEntity<MutableMap<String, Any>> {
        return ResponseEntity.ok(authService.register(regDto))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody loginCredentials: LoginCredentials): MutableMap<String, Any> {
        return authService.login(loginCredentials)
    }
}