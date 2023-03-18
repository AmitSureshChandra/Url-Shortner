package com.github.amitsureshchandra.urlshortner.service

import com.github.amitsureshchandra.urlshortner.dto.LoginCredentials
import com.github.amitsureshchandra.urlshortner.dto.RegDto
import com.github.amitsureshchandra.urlshortner.dto.UserDto
import com.github.amitsureshchandra.urlshortner.entity.User
import com.github.amitsureshchandra.urlshortner.exception.NotFoundException
import com.github.amitsureshchandra.urlshortner.exception.ValidationException
import com.github.amitsureshchandra.urlshortner.repo.UserRepo
import com.github.amitsureshchandra.urlshortner.utils.JwtUtil
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    val userRepo: UserRepo,
    val jwtUtil: JwtUtil,
    val authenticationManager: AuthenticationManager,
    val passwordEncoder: PasswordEncoder
) {
    val logger = LoggerFactory.getLogger(AuthService::class.java    )
    fun register(regDto: RegDto): MutableMap<String, Any> {

        // check if email already exists
        if(userRepo.findByEmailOrName(regDto.email!!, regDto.userName!!).isPresent())
            throw ValidationException("username or email already exists");

        val user = User(regDto.userName, regDto.email, regDto.mobile!!, passwordEncoder.encode(regDto.password))
        userRepo.save(user)
        val map = mutableMapOf<String, Any>()
        map["jwt-token"] = jwtUtil.generateToken(user.email)
        map["user"] = UserDto(user.email, user.name, user.mobile)
        return map
    }

    fun login(loginCredentials: LoginCredentials): MutableMap<String, Any> {
        try {
            val authToken = UsernamePasswordAuthenticationToken(loginCredentials.email!!, loginCredentials.password!!)
            authenticationManager.authenticate(authToken)
            val user = userRepo.findByEmail(loginCredentials.email) ?: throw NotFoundException("user not found")
            val map = mutableMapOf<String, Any>()
            map["jwt-token"] = jwtUtil.generateToken(loginCredentials.email)
            map["user"] = UserDto(user.email, user.name, user.mobile)
            return map
        }catch (exception: AuthenticationException) {
            logger.error(exception.message)
            throw ValidationException("Invalid Login Credentials")
        }
    }
}
