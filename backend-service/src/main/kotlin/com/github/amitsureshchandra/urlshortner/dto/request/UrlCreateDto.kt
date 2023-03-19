package com.github.amitsureshchandra.urlshortner.dto.request

import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class UrlCreateDto(
    @field:Pattern(regexp = "^https?:\\/\\/(?:www\\.)?(?!.*\\.\\.)(?!.*\\.$)[^\\s\\/\\?#]+(?:\\.[^\\s\\/\\?#]+)*(?:\\/[^\\s?#]*)*(?:\\?[^\\s#]*)?(?:#[^\\s]*)?$", message = "invalid url")
    val url: String? = null,

    @field:Size(max = 10, message = "length should be 10 or lesser")
    val shortUrl: String? = null
)
