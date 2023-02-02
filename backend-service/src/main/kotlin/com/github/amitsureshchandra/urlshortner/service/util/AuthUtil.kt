package com.github.amitsureshchandra.urlshortner.service.util

import com.github.amitsureshchandra.urlshortner.entity.User
import com.github.amitsureshchandra.urlshortner.repo.UserRepo
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service

@Service
class AuthUtil(val userRepo: UserRepo) {
    fun getAuthEmail(): String{
        val authentication = SecurityContextHolder.getContext().authentication
        val jwt = authentication.principal as Jwt
        return jwt.claims["email"].toString()
    }

    fun getAuthUser(): User{
        return userRepo.findByEmail(getAuthEmail())
    }
}