package com.github.amitsureshchandra.urlshortner.service

import com.github.amitsureshchandra.urlshortner.dto.UserDto
import com.github.amitsureshchandra.urlshortner.exception.NotFoundException
import com.github.amitsureshchandra.urlshortner.repo.UrlRepo
import com.github.amitsureshchandra.urlshortner.repo.UserRepo
import com.github.amitsureshchandra.urlshortner.utils.AuthUtil
import org.springframework.stereotype.Service

@Service
class UserService(private val authUtil: AuthUtil, private val urlRepo: UrlRepo, val userRepo: UserRepo) {
    fun getAuthUser(): UserDto = userRepo.findDtoByEmail(authUtil.getAuthEmail()) ?: throw NotFoundException("not found")
}