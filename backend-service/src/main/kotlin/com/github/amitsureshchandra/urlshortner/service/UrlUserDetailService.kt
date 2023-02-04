package com.github.amitsureshchandra.urlshortner.service

import com.github.amitsureshchandra.urlshortner.exception.NotFoundException
import com.github.amitsureshchandra.urlshortner.repo.UserRepo
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.Collections

@Component
class UrlUserDetailService(val userRepo: UserRepo): UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepo.findByEmail(email) ?: throw NotFoundException("user not found")
        return User(
            email,
            user.getPass(),
            Collections.singletonList(SimpleGrantedAuthority("ROLE_USER"))
        )
    }
}