package com.github.amitsureshchandra.urlshortner.utils

import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class UrlUtil {
    fun createShortUrl(len: Int): String{
        return UUID.randomUUID().toString().subSequence(0, len).toString();
    }

    fun getServerPath(httpServletRequest: HttpServletRequest): String?{
        return httpServletRequest.requestURL.split("/api")[0]
    }
}