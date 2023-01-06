package com.github.amitsureshchandra.urlshortner.service

import com.github.amitsureshchandra.urlshortner.dto.UrlCreateDto
import org.springframework.stereotype.Service
import java.util.*

@Service
class UrlService {

    var urlMap: HashMap<String?, String?> = HashMap()
    fun saveUrl(dto: UrlCreateDto): String? {
        var shortUrl: String = UUID.randomUUID().toString().subSequence(0,7).toString()
        urlMap[shortUrl] = dto.url.orEmpty()
        return shortUrl
    }

}