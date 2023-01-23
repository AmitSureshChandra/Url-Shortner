package com.github.amitsureshchandra.urlshortner.repo

import com.github.amitsureshchandra.urlshortner.dto.UserUrl
import com.github.amitsureshchandra.urlshortner.entity.UrlMap
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface UrlRepo : CrudRepository<UrlMap, UUID>{
    @Query("SELECT new com.github.amitsureshchandra.urlshortner.dto.UserUrl(u.shortUrl, u.fullUrl) FROM UrlMap u WHERE u.user.email = :userEmail")
    fun findAllByUserEmail(userEmail: String): List<UserUrl>
}