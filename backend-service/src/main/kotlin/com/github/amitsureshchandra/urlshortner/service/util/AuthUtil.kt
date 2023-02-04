package com.github.amitsureshchandra.urlshortner.service.util

import com.github.amitsureshchandra.urlshortner.entity.User
import com.github.amitsureshchandra.urlshortner.exception.NotFoundException
import com.github.amitsureshchandra.urlshortner.repo.UserRepo
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthUtil(val userRepo: UserRepo) {
    fun getAuthEmail(): String{
        return SecurityContextHolder.getContext().authentication.principal.toString()
    }

    fun getAuthUser(): User{
        return userRepo.findByEmail(getAuthEmail()) ?: throw NotFoundException("user not found")
    }
}