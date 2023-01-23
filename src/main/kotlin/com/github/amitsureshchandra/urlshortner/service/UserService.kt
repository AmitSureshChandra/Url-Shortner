package com.github.amitsureshchandra.urlshortner.service

import com.github.amitsureshchandra.urlshortner.dto.UserUrl
import com.github.amitsureshchandra.urlshortner.repo.UrlRepo
import com.github.amitsureshchandra.urlshortner.service.util.AuthUtil
import org.springframework.stereotype.Service

@Service
class UserService(private val authUtil: AuthUtil, private val urlRepo: UrlRepo) {
    fun authUserRoutes(): List<UserUrl> {
        return urlRepo.findAllByUserEmail(authUtil.getAuthEmail())
    }
}