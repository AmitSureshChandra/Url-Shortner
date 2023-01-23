package com.github.amitsureshchandra.urlshortner.service

import com.github.amitsureshchandra.urlshortner.repo.UrlRepo
import com.github.amitsureshchandra.urlshortner.dto.UrlCreateDto
import com.github.amitsureshchandra.urlshortner.entity.UrlMap
import com.github.amitsureshchandra.urlshortner.entity.User
import com.github.amitsureshchandra.urlshortner.repo.UserRepo
import com.github.amitsureshchandra.urlshortner.service.util.AuthUtil
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct

@Service
class UrlService(val urlRepo: UrlRepo, val authUtil: AuthUtil, val userRepo: UserRepo) {

    var urlMap: HashMap<String, String> = HashMap()

    @PostConstruct
    private fun init() = urlRepo.findAll().forEach { urlMap[it.shortUrl] = it.fullUrl }

    fun saveUrl(dto: UrlCreateDto): String {
        var shortUrl: String = UUID.randomUUID().toString().subSequence(0,7).toString()
        urlMap[shortUrl] = dto.url
        saveUrlDB(dto, shortUrl)
        return shortUrl
    }

    private fun saveUrlDB(dto: UrlCreateDto, shortUrl: String) {
        val authUser: User = authUtil.getAuthUser()
        val urlMap: UrlMap = UrlMap(shortUrl, dto.url, null, authUser)
        authUser.urls.add(urlMap)
        userRepo.save(authUser)
    }

    fun getAllUrls(): HashMap<String, String> {
        return urlMap;
    }
}