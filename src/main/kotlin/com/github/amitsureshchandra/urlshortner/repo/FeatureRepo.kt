package com.github.amitsureshchandra.urlshortner.repo

import com.github.amitsureshchandra.urlshortner.entity.Feature
import org.springframework.data.jpa.repository.JpaRepository

interface FeatureRepo: JpaRepository<Feature, Long> {
    fun existsByCode(code: String): Boolean
}