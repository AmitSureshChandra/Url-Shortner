package com.github.amitsureshchandra.urlshortner.service

import com.github.amitsureshchandra.urlshortner.repo.UrlRepo
import com.github.amitsureshchandra.urlshortner.utils.AuthUtil
import org.springframework.stereotype.Service

@Service
class UserService(private val authUtil: AuthUtil, private val urlRepo: UrlRepo) {

}