package com.github.amitsureshchandra.urlshortner.dto

import javax.validation.constraints.Size

data class UrlCreateDto(val url: String = "", @field:Size(max = 6, message = "length should be 6 or lesser") val shortUrl: String = "")
