package com.github.amitsureshchandra.urlshortner.utils

import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class UrlUtils {
    fun getServerPath(httpServletRequest: HttpServletRequest): String?{
        return httpServletRequest.requestURL.split("/api")[0]
    }
}