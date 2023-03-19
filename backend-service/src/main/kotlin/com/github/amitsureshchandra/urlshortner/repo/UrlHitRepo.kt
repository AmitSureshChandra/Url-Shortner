package com.github.amitsureshchandra.urlshortner.repo

import com.github.amitsureshchandra.urlshortner.entity.UrlHit
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UrlHitRepo: JpaRepository<UrlHit, UUID> {
}