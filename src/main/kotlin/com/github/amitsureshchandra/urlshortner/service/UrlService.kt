package com.github.amitsureshchandra.urlshortner.service

import com.github.amitsureshchandra.urlshortner.UrlRepo
import com.github.amitsureshchandra.urlshortner.dto.UrlCreateDto
import com.github.amitsureshchandra.urlshortner.entity.UrlMap
import org.springframework.stereotype.Service
import java.util.*

@Service
class UrlService(val urlRepo: UrlRepo) {

    var urlMap: HashMap<String, String> = HashMap()

    fun saveUrl(dto: UrlCreateDto): String {
        var shortUrl: String = UUID.randomUUID().toString().subSequence(0,7).toString()
        urlMap[shortUrl] = dto.url
        saveUrlDB(dto, shortUrl)
        return shortUrl
    }

    private fun saveUrlDB(dto: UrlCreateDto, shortUrl: String) {
        val urlMap: UrlMap = UrlMap(shortUrl, dto.url, null)
        urlRepo.save(urlMap)
    }

    fun getAllUrls(): HashMap<String, String> {
        return urlMap;
    }
}