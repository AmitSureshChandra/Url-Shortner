package com.github.amitsureshchandra.urlshortner.service.util

import org.springframework.stereotype.Service
import java.util.*

@Service
class UrlUtil {
    fun createShortUrl(len: Int): String{
        return UUID.randomUUID().toString().subSequence(0, len).toString();
    }
}