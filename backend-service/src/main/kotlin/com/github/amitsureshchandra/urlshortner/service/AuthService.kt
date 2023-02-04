package com.github.amitsureshchandra.urlshortner.service

import com.github.amitsureshchandra.urlshortner.dto.LoginCredentials
import com.github.amitsureshchandra.urlshortner.dto.RegDto
import com.github.amitsureshchandra.urlshortner.entity.User
import com.github.amitsureshchandra.urlshortner.exception.ValidationException
import com.github.amitsureshchandra.urlshortner.repo.UserRepo
import com.github.amitsureshchandra.urlshortner.service.util.JwtUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    val userRepo: UserRepo,
    val jwtUtil: JwtUtil,
    val authenticationManager: AuthenticationManager,
    val passwordEncoder: PasswordEncoder
) {
    fun register(regDto: RegDto): MutableMap<String, String> {
        val user = User(regDto.userName, regDto.email, regDto.mobile, passwordEncoder.encode(regDto.password))
        userRepo.save(user)
        return Collections.singletonMap("jwt-token", jwtUtil.generateToken(user.email))
    }

    fun login(loginCredentials: LoginCredentials): MutableMap<String, String> {
        try {
            val authToken = UsernamePasswordAuthenticationToken(loginCredentials.email, loginCredentials.password)
            authenticationManager.authenticate(authToken)
            return Collections.singletonMap("jwt-token", jwtUtil.generateToken(loginCredentials.email))
        }catch (exception: AuthenticationException) {
            throw ValidationException("Invalid Login Credentials")
        }
    }
}
